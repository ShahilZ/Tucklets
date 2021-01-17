package com.tucklets.app.entities.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum DonationDuration
{
//    ONE_YEAR(1, "sponsor-info.donationDuration.ONE_YEAR"),
//    THREE_YEAR(2, "sponsor-info.donationDuration.THREE_YEARS"),
//    INDEFINITE(3, "sponsor-info.donationDuration.INDEFINITE"),
//    SIX_MONTHS(0, "sponsor-info.donationDuration.SIX_MONTHS");
    ONCE("ONCE"),
    MONTHLY("MONTHLY"),
    ANNUAL("YEARLY"),
    ANNUAL_RECURRING("YEARLY_RECURRING");


    private final String donationDurationText;

    DonationDuration(String donationDurationText) {
        this.donationDurationText = donationDurationText;
    }

    public String getDonationDuration() {
        return donationDurationText;
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
}