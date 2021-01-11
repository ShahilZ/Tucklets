package com.tucklets.app.controllers;

import com.google.gson.Gson;
import com.tucklets.app.containers.SponsorInfoContainer;
import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.SponsorAddress;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.entities.enums.SponsorInfoStatus;
import com.tucklets.app.entities.enums.State;
import com.tucklets.app.services.AmountService;
import com.tucklets.app.services.ChildAndSponsorAssociationService;
import com.tucklets.app.services.ChildService;
import com.tucklets.app.services.DonationService;
import com.tucklets.app.services.EmailService;
import com.tucklets.app.services.ManageChildrenService;
import com.tucklets.app.services.SponsorAndDonationAssociationService;
import com.tucklets.app.services.SponsorService;
import com.tucklets.app.utils.TextUtils;
import com.tucklets.app.validations.DonationValidator;
import com.tucklets.app.validations.SponsorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Email;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/sponsor-info")
public class SponsorInfoController {

    private static final Gson GSON = new Gson();

    @Autowired
    SponsorService sponsorService;

    @Autowired
    ChildAndSponsorAssociationService childAndSponsorAssociationService;

    @Autowired
    ChildService childService;

    @Autowired
    DonationService donationService;

    @Autowired
    AmountService amountService;

    @Autowired
    EmailService emailService;

    @Autowired
    ManageChildrenService manageChildrenService;

    @Autowired
    SponsorAndDonationAssociationService sponsorAndDonationAssociationService;

    @GetMapping(value = "/")
    public String handleSponsorInfoLanding() {
        // Redirect to root page.
        return "home";
    }

    @GetMapping(value = "/selections/")
    @ResponseBody
    public String handleChildSelection(@RequestParam(value = "childIds") String[] childrenIds) {
        // TODO: Validate request params?
        Long[] childIds = Arrays.stream(childrenIds).map(Long::parseLong).toArray(Long[]::new);

        var selectedChildren = childService.fetchChildByIds(childIds);
        var childrenDetailContainers = manageChildrenService.createChildDetailsContainers(selectedChildren);
        var totalDonationAmount = amountService.computeTotalDonationAmount(selectedChildren);
        Sponsor sponsor = new Sponsor();
        // For now, force monthly payment frequencey.
        Donation donation = new Donation(totalDonationAmount, DonationDuration.MONTHLY);

        SponsorInfoContainer sponsorInfoContainer = new SponsorInfoContainer(
            donation, sponsor, childrenDetailContainers);
        return GSON.toJson(sponsorInfoContainer);
    }

    @PostMapping(value = "/submit")
    @ResponseBody
    public ResponseEntity<String> handleSponsorSubmission(@RequestBody SponsorInfoContainer sponsorInfoContainer) {

        Sponsor sponsor = sponsorInfoContainer.getSponsor();
        Donation donation = sponsorInfoContainer.getDonation();

        donation.setDonationDuration(donation.getDonationDuration());

        // Validation for sponsor + donation.
        SponsorInfoStatus sponsorStatus = SponsorValidator.validateSponsor(sponsor);
        boolean isValidDonation = DonationValidator.validateDonation(donation);

        if (!isValidDonation || sponsorStatus != SponsorInfoStatus.SUCCESS) {
            return ResponseEntity.badRequest().body(GSON.toJson(SponsorInfoStatus.ERROR));
        }

        // Add donation info.
        donationService.addDonation(donation);

        // Add sponsor info.
        sponsor = copyAndCleanSponsor(sponsor);
        sponsorService.addSponsor(sponsor);

        sponsorAndDonationAssociationService.createAssociation(sponsor, donation);
        List<ChildDetailsContainer> childrenContainer = sponsorInfoContainer.getChildren();


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
            emailService.sendConfirmationEmail(sponsor, children, donation, EmailService.PRESIDENT_EMAIL_ADDRESS);

        }
        else {
            // TODO: Generic sponsorship flow; send different email.
            emailService.sendGenericConfirmationEmail(sponsor, donation, sponsor.getEmail());
            emailService.sendGenericConfirmationEmail(sponsor, donation, EmailService.PRESIDENT_EMAIL_ADDRESS);
        }

        return ResponseEntity.ok(GSON.toJson(sponsorStatus));
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
