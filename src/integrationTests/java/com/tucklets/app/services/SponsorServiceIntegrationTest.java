package com.tucklets.app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("local")
@SpringBootTest
public class SponsorServiceIntegrationTest {

    @Autowired
    SponsorService sponsorService;

    @Test
    public void testAddCancelSubscription() {

    }
}
