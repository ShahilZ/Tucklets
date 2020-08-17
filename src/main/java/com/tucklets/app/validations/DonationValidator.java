package com.tucklets.app.validations;

import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.entities.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.Arrays;

public class DonationValidator {

    private DonationValidator() {};

    public static boolean validateDonation(Donation donation) {
        return
            // Verify payment method is a supported payment method.
            Arrays.asList(PaymentMethod.values()).contains(donation.getPaymentMethod())
            // Verify donation duration is a supported donation duration.
            && DonationDuration.getAllDonationDurations().contains(donation.getDonationDuration())
            // Verify donation amount.
            && donation.getDonationAmount().compareTo(BigDecimal.ZERO) > 0;
    }
}
