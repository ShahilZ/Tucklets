package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecretsConfig {
    private final String payPalClientId;

    /* There are the variables stored in AWS Secrets Manager*/
    public SecretsConfig(
            @Value("${paypal_client_id}") String payPalClientId)
    {
        this.payPalClientId = payPalClientId;
    }

    public String getPayPalClientId() { return payPalClientId; }
}
