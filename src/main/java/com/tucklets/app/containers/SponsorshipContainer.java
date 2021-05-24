package com.tucklets.app.containers;

import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;

import java.util.List;

public class SponsorshipContainer {

    private final Donation donation;
    private final Sponsor sponsor;
    private final List<ChildDetailsContainer> children;
    private final BrainTreePaymentContainer brainTreePaymentContainer;

    public SponsorshipContainer(
        Donation donation,
        Sponsor sponsor,
        List<ChildDetailsContainer> children,
        BrainTreePaymentContainer brainTreePaymentContainer) {
        this.donation = donation;
        this.sponsor = sponsor;
        this.children = children;
        this.brainTreePaymentContainer = brainTreePaymentContainer;
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

    public BrainTreePaymentContainer getBrainTreePaymentContainer() {
        return brainTreePaymentContainer;
    }
}
