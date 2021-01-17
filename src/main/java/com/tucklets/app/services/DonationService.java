package com.tucklets.app.services;

import com.tucklets.app.db.repositories.DonationRepository;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.utils.CalculationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DonationService {

    @Autowired
    DonationRepository donationRepository;

    public void addDonation(Donation donation) {
        // Sets creation date to be the current date.
        donation.setCreationDate(new Date());
        donationRepository.save(donation);
    }

    /**
     * Computes the new donation amount to be used given the desired donation duration.
     */
    public Map<String, String> changeDonationDuration(DonationDuration duration, DonationDuration previousDuration, BigDecimal amount) {
        BigDecimal newAmount = CalculationUtils.calculateAmount(duration, previousDuration, amount);
        HashMap<String, String> donationMap = new HashMap<>();
        donationMap.put("amount", newAmount.toString());
        return donationMap;
    }

}
