package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretsConfig {

    private final String payPalClientId;
    private final String brainTreeClientId;

    /* These are the variables stored in AWS Secrets Manager */
    public SecretsConfig
    (@Value("${paypal_client_id}") String payPalClientId, @Value("${braintree_client_id}") String brainTreeClientId)
    {
        this.payPalClientId = payPalClientId;
        this.brainTreeClientId = brainTreeClientId;
    }

    public String getPayPalClientId() {
        return payPalClientId;
    }

    public String getBrainTreeClientId() {
        return brainTreeClientId;
    }
}
