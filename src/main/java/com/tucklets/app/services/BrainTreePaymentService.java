package com.tucklets.app.services;

import com.braintreegateway.*;
import com.tucklets.app.configs.AppConfig;
import com.tucklets.app.configs.SecretsConfig;
import com.tucklets.app.db.repositories.SubscriptionRepository;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;


@Service
public class BrainTreePaymentService {

    private final AppConfig appConfig;
    private final SecretsConfig secretsConfig;
    private final BraintreeGateway braintreeGateway;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    BrainTreePaymentService(AppConfig appConfig, SecretsConfig secretsConfig) {
        this.appConfig = appConfig;
        this.secretsConfig = secretsConfig;

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
    protected Result<com.braintreegateway.Subscription> processSubscription(Donation donation, Customer customer) {
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest()
                .paymentMethodToken(customer.getPaymentMethods().get(0).getToken())
                .planId(donation.getDonationDuration().getBrainTreePlanId())
                .price(donation.getDonationAmount());
        return braintreeGateway.subscription().create(subscriptionRequest);
    }

    /**
     * Creates a customer based off of the given Sponsor object for BrainTree's consumption.
     * Note: Assumes a valid sponsor object.
     */
    protected Customer createBrainTreeCustomer(Sponsor sponsor, String paymentMethodNonce) {
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
    protected void addSubscription(Customer brainTreeCustomer,
                                   com.braintreegateway.Subscription brainTreeSubscription,
                                   Sponsor sponsor) {
        Subscription subscription = new Subscription();
        subscription.setBrainTreeCustomerId(brainTreeCustomer.getId());
        subscription.setBrainTreeSubscriptionId(brainTreeSubscription.getId());
        subscription.setSponsorId(sponsor.getSponsorId());
        subscriptionRepository.save(subscription);
    }



}
