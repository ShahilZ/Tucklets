package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretsConfig {

    private final String payPalClientId;
    private final String brainTreeMerchantId;
    private final String brainTreePublicKey;
    private final String brainTreePrivateKey;


    /* These are the variables stored in AWS Secrets Manager */
    public SecretsConfig(
            @Value("${paypal_client_id}") String payPalClientId,
            @Value("${braintree_merchant_id}") String brainTreeMerchantId,
            @Value("${braintree_public_key}") String brainTreePublicKey,
            @Value("${braintree_private_key}") String brainTreePrivateKey )
    {
        this.payPalClientId = payPalClientId;
        this.brainTreeMerchantId = brainTreeMerchantId;
        this.brainTreePublicKey = brainTreePublicKey;
        this.brainTreePrivateKey = brainTreePrivateKey;
    }

    public String getPayPalClientId() {
        return payPalClientId;
    }

    public String getBrainTreeMerchantId() {  return brainTreeMerchantId; }

    public String getBrainTreePublicKey() {  return brainTreePublicKey; }

    public String getBrainTreePrivateKey() {  return brainTreePrivateKey; }
}
