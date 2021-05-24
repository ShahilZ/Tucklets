package com.tucklets.app.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum DonationDuration
{
//    ONE_YEAR(1, "sponsor-info.donationDuration.ONE_YEAR"),
//    THREE_YEAR(2, "sponsor-info.donationDuration.THREE_YEARS"),
//    INDEFINITE(3, "sponsor-info.donationDuration.INDEFINITE"),
//    SIX_MONTHS(0, "sponsor-info.donationDuration.SIX_MONTHS");
    ONCE("ONCE", "no-plan"),
    MONTHLY("MONTHLY", "MonthlySponsorship"),
    YEARLY("YEARLY", "YearlySponsorship"),
    YEARLY_RECURRING("YEARLY_RECURRING", "no-plan-recurring-yearly");


    private final String donationDurationText;
    private final String brainTreePlanId;

    DonationDuration(String donationDurationText, String brainTreePlanId) {
        this.donationDurationText = donationDurationText;
        this.brainTreePlanId = brainTreePlanId;
    }

    public String getDonationDuration() {
        return donationDurationText;
    }

    public String getBrainTreePlanId() {
        return brainTreePlanId;
    }

    public static DonationDuration of(String donationDurationText) {
        return Stream.of(DonationDuration.values())
                .filter(p -> p.getDonationDuration().equals(donationDurationText))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Returns all the donation frequencies as a list.
     */
    public static List<DonationDuration> getAllDonationDurations() {
        return Stream.of(DonationDuration.values()).collect(Collectors.toList());
    }


    /**
     * Set of donation durations that are not recurring.
     */
    public static Set<DonationDuration> NON_RECURRING_DONATION_DURATIONS =
            Set.of(DonationDuration.ONCE, DonationDuration.YEARLY);


    @JsonCreator
    public static DonationDuration forValue(Map<String, String> jsonObject) {
        return DonationDuration.of(jsonObject.get("value"));
    }
}
