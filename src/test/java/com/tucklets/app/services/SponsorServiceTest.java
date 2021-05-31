package com.tucklets.app.services;

import com.tucklets.app.configs.AppConfig;
import com.tucklets.app.db.repositories.SponsorRepository;
import com.tucklets.app.entities.Sponsor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SponsorServiceTest {

    @Mock
    SponsorRepository sponsorRepository;
    @Mock
    AppConfig appConfig;
    @Mock
    ManageSponsorService manageSponsorService;
    @Mock
    EmailService emailService;
    @Mock
    DonationService donationService;
    @Mock
    SponsorAndDonationAssociationService sponsorAndDonationAssociationService;
    @Mock
    ChildService childService;
    @Mock
    BrainTreePaymentService brainTreePaymentService;
    @Mock
    ChildAndSponsorAssociationService childAndSponsorAssociationService;
    @Mock
    ManageChildrenService manageChildrenService;
    @Mock
    AmountService amountService;

    SponsorService sponsorService;

    @BeforeEach
    public void setUp() {
        sponsorRepository = Mockito.mock(SponsorRepository.class, "SponsorRepositoryMock");
        sponsorService = new SponsorService(
            appConfig,
            sponsorRepository,
            manageSponsorService,
            emailService,
            donationService,
            sponsorAndDonationAssociationService,
            childService,
            brainTreePaymentService,
            childAndSponsorAssociationService,
            manageChildrenService,
            amountService);
    }

    @Test
    public void testFindSponsorByEmail(){
        String sponsorEmail = "test@yahoo.com";

        // Expected value
        Sponsor expectedSponsor = new Sponsor();
        expectedSponsor.setEmail(sponsorEmail);

        // Set up mocks.
        Mockito.when(sponsorRepository.fetchSponsorByEmail(sponsorEmail)).thenReturn(Optional.of(expectedSponsor));

        Assertions.assertEquals(expectedSponsor, sponsorService.fetchSponsorByEmail(sponsorEmail).get());
    }
}
