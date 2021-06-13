package com.tucklets.app.validations;

import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.entities.enums.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;

public class DonationValidatorTest {

    @Test
    public void testNullDonation() {
        Donation nullDonation = new Donation();
        Assert.isTrue(
                !DonationValidator.validateDonation(nullDonation),
                "Null donation is not a valid donation!"
        );
    }

    @Test
    public void testZeroDonationAmount() {
        Donation zeroDonation = new Donation();
        zeroDonation.setDonationAmount(BigDecimal.ZERO);

        Assert.isTrue(
                !DonationValidator.validateDonation(zeroDonation),
                "Donation is invalid because it is of a zero amount."
        );
    }

    @Test
    public void testValidDonation() {
        Donation validDonation = new Donation();
        validDonation.setDonationAmount(BigDecimal.TEN);
        validDonation.setDonationDuration(DonationDuration.MONTHLY);
        validDonation.setPaymentMethod(PaymentMethod.OTHER);

        Assert.isTrue(
                DonationValidator.validateDonation(validDonation),
                "Given donation is a valid donation."
        );
    }

}
