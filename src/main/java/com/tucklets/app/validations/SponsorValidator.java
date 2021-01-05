package com.tucklets.app.validations;

import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.SponsorInfoStatus;
import com.tucklets.app.utils.TextUtils;
import org.apache.commons.lang3.StringUtils;

public class SponsorValidator {

    private SponsorValidator() {};

    /**
     * Given a sponsor object, validate that the fields for the sponsor are valid.
     * @returns SponsorInfoStatus detailing the results of the validation.
     */
    public static SponsorInfoStatus validateSponsor(Sponsor sponsor) {
        // Validate that first + last name are both not null.
        if (StringUtils.isNotBlank(TextUtils.cleanString(sponsor.getFirstName()))
            && StringUtils.isNotBlank(TextUtils.cleanString(sponsor.getLastName()))
            // Validate that the email address is not blank.
            && StringUtils.isNotBlank(TextUtils.cleanString(sponsor.getEmail()))
            // Verify that the email address contains an '@'
            && sponsor.getEmail().contains("@")
            // Verify that the email address contains '.'
            && sponsor.getEmail().contains(".")
        ) {
            return SponsorInfoStatus.SUCCESS;
        }

        return SponsorInfoStatus.ERROR;

    }
}
