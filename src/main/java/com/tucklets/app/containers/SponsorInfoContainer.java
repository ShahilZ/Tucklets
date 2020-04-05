package com.tucklets.app.containers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationDuration;

import java.util.List;

public class SponsorInfoContainer {

    private Sponsor sponsor;
    private List<Child> children;
    private List<DonationDuration> donationDurations;
    private String[] selectedChildIds;
    private DonationDuration selectedDonationDuration;
    private int numChildren;

    public SponsorInfoContainer(
        Sponsor sponsor,
        List<Child> children,
        List<DonationDuration> donationDurations,
        String[] selectedChildIds,
        DonationDuration selectedDonationDuration,
        int numChildren)
    {
        this.sponsor = sponsor;
        this.children = children;
        this.donationDurations = donationDurations;
        this.selectedChildIds = selectedChildIds;
        this.selectedDonationDuration = selectedDonationDuration;
        this.numChildren = numChildren;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<DonationDuration> getDonationDurations() {
        return donationDurations;
    }

    public void setDonationDurations(List<DonationDuration> donationDurations) {
        this.donationDurations = donationDurations;
    }

    public String[] getSelectedChildIds() {
        return selectedChildIds;
    }

    public void setSelectedChildIds(String[] selectedChildIds) {
        this.selectedChildIds = selectedChildIds;
    }

    public DonationDuration getSelectedDonationDuration() {
        return selectedDonationDuration;
    }

    public void setSelectedDonationDuration(DonationDuration selectedDonationDuration) {
        this.selectedDonationDuration = selectedDonationDuration;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }
}
