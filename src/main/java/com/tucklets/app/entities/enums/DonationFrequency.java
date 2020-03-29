package com.tucklets.app.entities.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum DonationFrequency
{
    ONE_TIME(0, "One Time"), MONTHLY(1, "Monthly"), YEARLY(2, "Annually");

    private int donationFrequencyValue;
    private String displayText;

    DonationFrequency(int donationFrequencyValue, String displayText) {
        this.donationFrequencyValue = donationFrequencyValue;
        this.displayText = displayText;
    }

    public int getDonationFrequency() {
        return donationFrequencyValue;
    }

    public static DonationFrequency of(int donationFrequencyValue) {
        return Stream.of(DonationFrequency.values())
                .filter(p -> p.getDonationFrequency() == donationFrequencyValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Returns all the donation frequencies as a list.
     */
    public static List<DonationFrequency> getAllDonationFrequencies() {
        return Stream.of(DonationFrequency.values()).collect(Collectors.toList());
    }

    public String getDisplayText() {
        return displayText;
    }
}