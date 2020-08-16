package com.tucklets.app.containers;

import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;

import java.util.List;

public class SponsorInfoContainer {

    private Donation donation;
    private Sponsor sponsor;
    private List<ChildDetailsContainer> children;

    public SponsorInfoContainer(
        Donation donation,
        Sponsor sponsor,
        List<ChildDetailsContainer> children) {
        this.donation = donation;
        this.sponsor = sponsor;
        this.children = children;
    }

    public Donation getDonation() {
        return donation;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public List<ChildDetailsContainer> getChildren() {
        return children;
    }

    public void setChildren(List<ChildDetailsContainer> children) {
        this.children = children;
    }

}
