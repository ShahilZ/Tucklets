package com.tucklets.app.configs.service;

import com.tucklets.app.services.SponsorService;
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
