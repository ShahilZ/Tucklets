package com.tucklets.app.services;

import com.tucklets.app.db.repositories.DonationRepository;
import com.tucklets.app.entities.Donation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DonationService {

    @Autowired
    DonationRepository donationRepository;

    public void addDonation(Donation donation) {
        // Sets creation date to be the current date.
        donation.setCreationDate(new Date());
        donationRepository.save(donation);
    }

}
