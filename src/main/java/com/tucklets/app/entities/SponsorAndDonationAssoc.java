package com.tucklets.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sponsor_donation_assoc")
/**
 * Class representing the association of a sponsor to his or her donation. The ids mentioned are the unique ids for each entity.
 */
public class SponsorAndDonationAssoc {
    @Id
    @SequenceGenerator(name = "sponsor_donation_assoc_generator", initialValue = 3500, allocationSize = 1)
    @GeneratedValue(generator = "sponsor_donation_assoc_generator")
    @Column(name = "sponsor_donation_assoc_id", updatable = false, nullable = false)
    private Long sponsorDonationAssocId;

    @Column(name = "sponsor_id", updatable = false, nullable = false)
    private long sponsorId;

    @Column(name = "donation_id", updatable = false, nullable = false)
    private long donationId;

    public SponsorAndDonationAssoc(){};

    public SponsorAndDonationAssoc(long sponsorId, long donationId) {
        this.sponsorId = sponsorId;
        this.donationId = donationId;
    }
}
