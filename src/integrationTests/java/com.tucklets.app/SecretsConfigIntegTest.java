package com.tucklets.app;

import com.tucklets.app.configs.SecretsConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecretsConfigIntegTest {

    @Autowired
    SecretsConfig secretsConfig;

    @Test
    public void testPayPalClientIdNotNull() {
        // Verify non-null paypal client id.
        Assert.assertNotNull(secretsConfig.getPayPalClientId());
    }

}
