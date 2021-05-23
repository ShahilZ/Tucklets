package com.tucklets.app.services;

import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.braintreegateway.Transaction;
import com.google.gson.Gson;
import com.tucklets.app.configs.AppConfig;
import com.tucklets.app.containers.BrainTreePaymentContainer;
import com.tucklets.app.containers.SponsorshipContainer;
import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.db.repositories.SponsorRepository;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.SponsorAddress;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.entities.enums.SponsorInfoStatus;
import com.tucklets.app.entities.enums.State;
import com.tucklets.app.utils.TextUtils;
import com.tucklets.app.validations.DonationValidator;
import com.tucklets.app.validations.SponsorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SponsorService {

    private static final Gson GSON = new Gson();

    @Autowired
    AppConfig appConfig;

    @Autowired
    SponsorRepository sponsorRepository;

    @Autowired
    ManageSponsorService manageSponsorService;

    @Autowired
    EmailService emailService;

    @Autowired
    DonationService donationService;

    @Autowired
    SponsorAndDonationAssociationService sponsorAndDonationAssociationService;

    @Autowired
    ChildService childService;

    @Autowired
    BrainTreePaymentService brainTreePaymentService;

    @Autowired
    ChildAndSponsorAssociationService childAndSponsorAssociationService;

    @Autowired
    ManageChildrenService manageChildrenService;

    @Autowired
    AmountService amountService;


    /**
     * Fetches all active sponsors
     */
    public List<Sponsor> fetchAllSponsors() {
        return sponsorRepository.fetchAllSponsors();
    }

    /**
     * Fetches all active sponsors who are subscribed to receive notifications.
     */
    public List<Sponsor> fetchAllSubscribedSponsors() {
        return sponsorRepository.fetchSubscribedSponsors();
    }

    public Sponsor fetchSponsorById(Long sponsorId) {
        Optional<Sponsor> possibleSponsor = sponsorRepository.fetchSponsorById(sponsorId);
        return possibleSponsor.orElse(null);
    }

    /**
     * Returns Optional<Sponnsor> based on the provided email.
     */
    public Optional<Sponsor> fetchSponsorByEmail(String email) {
        return sponsorRepository.fetchSponsorByEmail(email);
    }

    public void addSponsor(Sponsor sponsor) {
        Date today = new Date();
        sponsor.setLastUpdateDate(today);
        Optional<Sponsor> existingSponsorOptional = sponsorRepository.fetchSponsorByEmail(sponsor.getEmail());
        // Set creationDate if sponsor is not existing one.
        if (existingSponsorOptional.isEmpty()) {
            sponsor.setCreationDate(today);
        }
        else {
            // Set existing fields since this is an existing sponsor.
            manageSponsorService.addExistingFieldsToSponsor(sponsor, existingSponsorOptional.get());
        }
        // Create/update sponsor.
        sponsorRepository.save(sponsor);
    }

    /**
     * Soft deletes the child with the given id.
     */
    public void deleteSponsor(String email) {
        Optional<Sponsor> sponsor = sponsorRepository.fetchSponsorByEmail(email);
        sponsor.ifPresent(value -> value.setDeletionDate(new Date()));
        sponsorRepository.save(sponsor.get());
    }

    /**
     * Set subscribed column to false. Should be called with another method to validate sponosr.
     */
    private void unsubscribeSponsorNewsletter(Sponsor sponsor) {
        sponsor.setSubscribed(false);
        sponsorRepository.save(sponsor);
    }

    /**
     * Unsubscribes a sponsor from receiving newsletters. Do nothing if email does not correspond
     * to a sponsor in our db. This is to avoid giving too much info to the user.
     */
    public void unsubscribeSponsorFromNewsletter(String email) {
        Optional<Sponsor> sponsorOptional = sponsorRepository.fetchSponsorByEmail(email);
        if (sponsorOptional.isPresent()) {
            unsubscribeSponsorNewsletter(sponsorOptional.get());
        }
    }

    /**
     * Associated selected children to the SponsorshipContainer object.
     */
    public SponsorshipContainer associateSelectedChildren(String[] childrenIds) {

        Long[] childIds = Arrays.stream(childrenIds).map(Long::parseLong).toArray(Long[]::new);

        var selectedChildren = childService.fetchChildByIds(childIds);
        var childrenDetailContainers = manageChildrenService.createChildDetailsContainers(selectedChildren);
        var totalDonationAmount = amountService.computeTotalDonationAmount(selectedChildren);
        Sponsor sponsor = new Sponsor();
        // For now, force monthly payment frequency.
        Donation donation = new Donation(totalDonationAmount, DonationDuration.MONTHLY);

        SponsorshipContainer sponsorshipContainer = new SponsorshipContainer(
                donation, sponsor, childrenDetailContainers, new BrainTreePaymentContainer());

        return sponsorshipContainer;
    }


    /**
     * Processes the whole submission flow.
     * Braintree for processing payment
     * Calls helper method to save everything related to the sponsorship to database and send confirmation email
     */
    public SponsorInfoStatus processSponsorship(
            BrainTreePaymentContainer brainTreePaymentContainer,
            Sponsor sponsor,
            Donation donation,
            List<ChildDetailsContainer> childrenContainer)
    {


        //Process brainTree payment transaction
        var nonce = brainTreePaymentContainer.getPaymentNonce();
        var donationAmount =  donation.getDonationAmount();
        Customer brainTreeCustomer = null;

        // Validation for sponsor + donation.
        SponsorInfoStatus sponsorStatus = SponsorValidator.validateSponsor(sponsor);
        boolean isValidDonation = DonationValidator.validateDonation(donation);

        if (!isValidDonation || sponsorStatus != SponsorInfoStatus.SUCCESS) {
            return SponsorInfoStatus.ERROR;
        }

        // If donation is for one-time, then just process payment as usual.
        if (DonationDuration.NON_RECURRING_DONATION_DURATIONS.contains(donation.getDonationDuration())) {
            Result<Transaction> paymentResult = brainTreePaymentService.processPayment(nonce, donationAmount);
            // Stop processing if the payment is unsuccessful
            if (!paymentResult.isSuccess()) {
                return SponsorInfoStatus.ERROR;
            }
        }
        else {
            brainTreeCustomer = brainTreePaymentService.createBrainTreeCustomer(sponsor, nonce);
            Result<Subscription> subscriptionResult =
                    brainTreePaymentService.processSubscription(donation, brainTreeCustomer);
            // Stop processing if the payment is unsuccessful
            if (!subscriptionResult.isSuccess()) {
                //test this if getErrors displays GSON
                return SponsorInfoStatus.ERROR;
            }
        }

        // if payment successful, proceed with saving information to database
        SponsorInfoStatus sponsorInfoResult = processSponsorInformation(sponsor, donation, childrenContainer);

        // Create association with Customer/Subscription info if customer was created.
        if (brainTreeCustomer != null) {
            // TODO: Save info in new table that tracks customer + subscription ids.
        }

        return sponsorInfoResult;
    }

    /**
     * Helper method that does the following:
     * Saves sponsorship information to database - Sponsor's personal information, child's association, and donation information.
     * Calls another helper method to send confirmation email to sponsor and the president
     */
    private SponsorInfoStatus processSponsorInformation(Sponsor sponsor, Donation donation, List<ChildDetailsContainer> childrenContainer ) {

        donation.setDonationDuration(donation.getDonationDuration());

        // Validation for sponsor + donation.
        SponsorInfoStatus sponsorStatus = SponsorValidator.validateSponsor(sponsor);
        boolean isValidDonation = DonationValidator.validateDonation(donation);

        if (!isValidDonation || sponsorStatus != SponsorInfoStatus.SUCCESS) {
            return SponsorInfoStatus.ERROR;
        }

        // Add donation info.
        donationService.addDonation(donation);

        // Add sponsor info.
        sponsor = copyAndCleanSponsor(sponsor);
        addSponsor(sponsor);

        sponsorAndDonationAssociationService.createAssociation(sponsor, donation);

        // SponsorInfoContainer contains selected children, then create children assocs and send email regarding them.
        if (!childrenContainer.isEmpty()) {
            Long[] childIds = childrenContainer
                    .stream()
                    .map(childDetailsContainer -> childDetailsContainer.getChild().getChildId())
                    .toArray(Long[]::new);
            List<Child> children = childService.fetchChildByIds(childIds);
            childAndSponsorAssociationService.createAssociation(children, sponsor, donation.getDonationDuration());
            childService.setSponsoredChildren(children);
            emailService.sendConfirmationEmail(sponsor, children, donation, sponsor.getEmail());
            emailService.sendConfirmationEmail(sponsor, children, donation, appConfig.getPresidentEmail() );

        }
        else {
            // TODO: Generic sponsorship flow; send different email.
            emailService.sendGenericConfirmationEmail(sponsor, donation, sponsor.getEmail());
            emailService.sendGenericConfirmationEmail(sponsor, donation, appConfig.getPresidentEmail());
        }
        return SponsorInfoStatus.SUCCESS;
    }

    /**
     * Returns JSON object with errors if the given Result<T> is not successful.
     */
    private <T> String createResultErrorsJson(Result<T> result) {
        return GSON.toJson(result.getErrors());
    }

    /**
     * Helper method that copies over the fields of a sponsor object and cleans the text as well.
     */
    private Sponsor copyAndCleanSponsor(Sponsor originalSponsor) {
        Sponsor newSponsor = new Sponsor();
        newSponsor.setEmail(TextUtils.cleanString(originalSponsor.getEmail()));
        newSponsor.setFirstName(TextUtils.cleanString(originalSponsor.getFirstName()));
        newSponsor.setLastName(TextUtils.cleanString(originalSponsor.getLastName()));
        // Clean address fields.
        newSponsor.setAddress(copyAndCleanSponsorAddress(originalSponsor.getAddress()));
        newSponsor.setChurchName(TextUtils.cleanString(originalSponsor.getChurchName()));
        newSponsor.setSubscribed(originalSponsor.isSubscribed());
        return newSponsor;
    }

    /**
     * Helper method to clean the address (if one was provided)
     */
    private SponsorAddress copyAndCleanSponsorAddress(SponsorAddress address) {
        // if address is null, return empty address.
        if (address == null) {
            return new SponsorAddress();
        }
        String streetAddress1 = TextUtils.cleanString(address.getStreetAddress1());
        String streetAddress2 = TextUtils.cleanString(address.getStreetAddress2());
        String city = TextUtils.cleanString(address.getCity());
        // Validate state.
        State state = State.CA;
        String zipCode = TextUtils.cleanString(address.getZipCode());
        String country = TextUtils.cleanString(address.getCountry());

        return new SponsorAddress(streetAddress1, streetAddress2, city, state, zipCode, country);
    }
}

