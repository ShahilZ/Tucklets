package com.tucklets.app.services;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.braintreegateway.SubscriptionRequest;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.exceptions.NotFoundException;
import com.tucklets.app.configs.AppConfig;
import com.tucklets.app.configs.SecretsConfig;
import com.tucklets.app.db.repositories.SponsorBrainTreeDetailRepository;
import com.tucklets.app.db.repositories.SponsorRepository;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.SponsorBrainTreeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class BrainTreePaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrainTreePaymentService.class);

    private final AppConfig appConfig;
    private final SecretsConfig secretsConfig;
    private final SponsorRepository sponsorRepository;
    private final SponsorBrainTreeDetailRepository sponsorBrainTreeDetailRepository;
    private final BraintreeGateway braintreeGateway;

    @Autowired
    BrainTreePaymentService(
        AppConfig appConfig,
        SecretsConfig secretsConfig,
        SponsorRepository sponsorRepository,
        SponsorBrainTreeDetailRepository sponsorBrainTreeDetailRepository)
    {
        this.appConfig = appConfig;
        this.secretsConfig = secretsConfig;
        this.sponsorRepository = sponsorRepository;
        this.sponsorBrainTreeDetailRepository = sponsorBrainTreeDetailRepository;

        // Initialize BrainTreeGateway once.
        braintreeGateway = createBrainTreeGateway();

    }

    /**
     * Creates a braintreeGateway object to be used when calling braintree methods
     * @return BraintreeGateway object
     */
    private BraintreeGateway createBrainTreeGateway() {
        return new BraintreeGateway(
                appConfig.getBrainTreeEnvironment().equals("PRODUCTION") ? Environment.PRODUCTION : Environment.SANDBOX,
                secretsConfig.getBrainTreeMerchantId(),
                secretsConfig.getBrainTreePublicKey(),
                secretsConfig.getBrainTreePrivateKey()
        );
    }

    /**
     * Processes payment and submits for settlement.
     * @return Result<Transaction>
     */
    protected Result<Transaction> processPayment(String nonce, BigDecimal amount) {
        //create transaction
        TransactionRequest request = new TransactionRequest()
                .amount(amount)
                .paymentMethodNonce(nonce)
//                .deviceData(deviceDataFromTheClient)
                .options()
                .submitForSettlement(true)
                .done();;
        return braintreeGateway.transaction().sale(request);
    }

    /**
     * Process subscription request for BrainTree based on donation duration.
     */
    protected Result<Subscription> processSubscription(Donation donation, Customer customer) {
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest()
                .paymentMethodToken(customer.getPaymentMethods().get(0).getToken())
                .planId(donation.getDonationDuration().getBrainTreePlanId())
                .price(donation.getDonationAmount());
        return braintreeGateway.subscription().create(subscriptionRequest);
    }

    /**
     * Makes a request to BrainTree to figure out if the request belongs to an existing customer.
     */
    protected Optional<Customer> findCustomer(String email) {
        Optional<Sponsor> sponsorOptional = sponsorRepository.fetchSponsorByEmail(email);
        Customer customer = null;
        if (sponsorOptional.isPresent()) {
            Sponsor sponsor = sponsorOptional.get();
            Optional<SponsorBrainTreeDetail> sponsorBrainTreeDetailOptional =
                sponsorBrainTreeDetailRepository.findBySponsorId(sponsor.getSponsorId());
            if (sponsorBrainTreeDetailOptional.isEmpty()) {
                // Missing SponsorBrainTreeDetail for some reason.
                LOGGER.warn("Missing SponsorBrainTreeDetail for sponsorId: {}", sponsor.getSponsorId());
            }
            else {
                SponsorBrainTreeDetail sponsorBrainTreeDetail = sponsorBrainTreeDetailOptional.get();
                try {
                    customer = braintreeGateway
                        .customer().find(sponsorBrainTreeDetail.getBrainTreeCustomerId());
                }
                catch (NotFoundException nfe) {
                    LOGGER.error(
                        "Missing in BrainTree sponsor id: {} and BrainTree customer id: {}.\n Exception: {}",
                        sponsor.getSponsorId(),
                        sponsorBrainTreeDetail.getBrainTreeCustomerId(),
                        nfe.getStackTrace());
                }
            }
        }
        return customer == null ? Optional.empty() : Optional.of(customer);
    }

    /**
     * Creates a customer based off of the given Sponsor object for BrainTree's consumption.
     * Note: Assumes a valid sponsor object.
     */
    protected Customer createBrainTreeCustomer(Sponsor sponsor, String paymentMethodNonce) {
        // Determine if customer is existing customer.
        Optional<Customer> customerOptional = findCustomer(sponsor.getEmail());

        if (customerOptional.isPresent()) {
            return customerOptional.get();
        }

        CustomerRequest customerRequest = new CustomerRequest()
                .firstName(sponsor.getFirstName())
                .lastName(sponsor.getLastName())
                .email(sponsor.getEmail())
                .customField("church", sponsor.getChurchName())
                .phone(sponsor.getPhoneNumber())
                .paymentMethodNonce(paymentMethodNonce);
        Result<Customer> result = braintreeGateway.customer().create(customerRequest);

        // TODO: Determine what to do if customer fails...
        if (!result.isSuccess()) {
            return null;
        }
        return result.getTarget();
    }

    /**
     * Saving brainTree information such as the customerId and subscriptionId
     * Note: Assumes a valid sponsor and Customer object.
     */
    protected void addSubscription(
        Customer brainTreeCustomer,
        Subscription brainTreeSubscription,
        Sponsor sponsor)
    {
        SponsorBrainTreeDetail sponsorBrainTreeDetail = new SponsorBrainTreeDetail();
        sponsorBrainTreeDetail.setBrainTreeCustomerId(brainTreeCustomer.getId());
        sponsorBrainTreeDetail.setBrainTreeSubscriptionId(brainTreeSubscription.getId());
        sponsorBrainTreeDetail.setSponsorId(sponsor.getSponsorId());
        sponsorBrainTreeDetailRepository.save(sponsorBrainTreeDetail);
    }

    /**
     * Given the subscriptionId, cancel the BrainTree subscription.
     */
    protected boolean cancelSubscription(String subscriptionId) {
        Result<Subscription> cancellationResult = braintreeGateway.subscription().cancel(subscriptionId);
        return cancellationResult.isSuccess();
    }

}
