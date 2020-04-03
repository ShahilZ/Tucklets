package com.tucklets.app.containers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationDuration;

import java.util.List;

public class ChildAndSponsorContainer {

    private Sponsor sponsor;
    private List<Child> children;
    private DonationDuration donationDuration;
    private int numChildren;


    public List<Child> getChildren() { return children; }

    public void setChildren(List<Child> children) { this.children = children; }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public DonationDuration getDonationDuration() {  return donationDuration;  }

    public void setDonationDuration(DonationDuration donationDuration) { this.donationDuration = donationDuration; }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }
}
