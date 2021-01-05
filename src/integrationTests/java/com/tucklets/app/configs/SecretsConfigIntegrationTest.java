package com.tucklets.app.configs;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SecretsConfig.class)
public class SecretsConfigIntegrationTest {

    @Autowired
    SecretsConfig secretsConfig;

    @Test
    public void testPayPalClientIdNotNull() {
        // Verify non-null paypal client id.
        Assert.assertNotNull(secretsConfig.getPayPalClientId());
    }

}
