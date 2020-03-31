package com.tucklets.app.entities.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum DonationDuration
{
    SIX_MONTHS(0, "6 months"),
    ONE_YEAR(1, "1 year"),
    THREE_YEAR(2, "3 years"),
    INDEFINITE(3, "Indefinite");

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