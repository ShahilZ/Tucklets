package com.tucklets.app.entities.enums;

import java.util.stream.Stream;


public enum DonationFrequency
{
    ONE_TIME(1), MONTHLY(2), YEARLY(3);

    private int donationFrequencyValue;

    DonationFrequency(int donationFrequencyValue) {
        this.donationFrequencyValue = donationFrequencyValue;
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
}