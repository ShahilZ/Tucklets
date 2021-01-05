package com.tucklets.app.validations;

import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.SponsorInfoStatus;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class SponsorValidatorTest {

    @Test
    public void testNullSponsor() {
        Sponsor sponsor = new Sponsor();
        Assert.isTrue(
                SponsorInfoStatus.ERROR == SponsorValidator.validateSponsor(sponsor),
                "Empty sponsor should have resulted in error message"
        );
    }

    @Test
    public void testInvalidSponsor() {
        Sponsor sponsor = new Sponsor();
        sponsor.setFirstName("<script> alert('js') </script>");
        sponsor.setLastName("");
        sponsor.setEmail("test@");

        Assert.isTrue(
                SponsorInfoStatus.ERROR == SponsorValidator.validateSponsor(sponsor),
                "Invalid sponsor should have resulted in error message"
        );
    }

    @Test
    public void testValidSponsor() {
        Sponsor sponsor = new Sponsor();
        sponsor.setFirstName("Valid");
        sponsor.setLastName("sponsor");
        sponsor.setEmail("test@tucklets.com");

        Assert.isTrue(
                SponsorInfoStatus.SUCCESS == SponsorValidator.validateSponsor(sponsor),
                "Invalid sponsor should have resulted in error message"
        );
    }
}
