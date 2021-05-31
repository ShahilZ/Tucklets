package com.tucklets.app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class BrainTreePaymentServiceIntegTest {

    @Test
    public void doNothing() {
        Assertions.assertTrue(true);
    }
}
