package com.tucklets.app.containers;

import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

public class SponsorInfoContainer {

    private final Donation donation;
    private final Sponsor sponsor;
    private final List<ChildDetailsContainer> children;

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

    public List<ChildDetailsContainer> getChildren() {
        return children;
    }


}
