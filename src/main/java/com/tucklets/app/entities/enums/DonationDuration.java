package com.tucklets.app.entities.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum DonationDuration
{
    ONE_YEAR(1, "sponsor-info.donationDuration.ONE_YEAR"),
    THREE_YEAR(2, "sponsor-info.donationDuration.THREE_YEARS"),
    INDEFINITE(3, "sponsor-info.donationDuration.INDEFINITE"),
    SIX_MONTHS(0, "sponsor-info.donationDuration.SIX_MONTHS");

    private int donationDurationValue;
    private String displayText;

    DonationDuration(int donationDurationValue, String displayText) {
        this.donationDurationValue = donationDurationValue;
        this.displayText = displayText;
    }

    public int getDonationDuration() {
        return donationDurationValue;
    }

    public static DonationDuration of(int donationDurationValue) {
        return Stream.of(DonationDuration.values())
                .filter(p -> p.getDonationDuration() == donationDurationValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Returns all the donation frequencies as a list.
     */
    public static List<DonationDuration> getAllDonationDurations() {
        return Stream.of(DonationDuration.values()).collect(Collectors.toList());
    }

    public String getDisplayText() {
        return displayText;
    }
}