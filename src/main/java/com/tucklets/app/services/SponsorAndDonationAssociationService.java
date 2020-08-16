package com.tucklets.app.services;

import com.tucklets.app.db.repositories.SponsorAndDonationAssociationRepository;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.SponsorAndDonationAssoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SponsorAndDonationAssociationService {

    @Autowired
    SponsorAndDonationAssociationRepository sponsorAndDonationAssociationRepository;

    public void createAssociation(Sponsor sponsor, Donation donation) {
        if (Objects.nonNull(donation) && Objects.nonNull(sponsor)) {
            SponsorAndDonationAssoc sponsorAndDonationAssoc= new SponsorAndDonationAssoc(sponsor.getSponsorId(), donation.getDonationId());
            sponsorAndDonationAssociationRepository.save(sponsorAndDonationAssoc);
        }
    }
}