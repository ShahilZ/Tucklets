package com.tucklets.app.entities;

import com.tucklets.app.entities.enums.DonationDuration;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "child_sponsor_assoc")
/**
 * Class representing the association of a child to his or her sponsor. The ids mentioned are the unique ids for each entity.
 */
public class ChildAndSponsorAssoc {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "childSponsorAssocId", updatable = false, nullable = false)
    private Long childSponsorAssocId;

    @Column(name = "child_id", updatable = false, nullable = false)
    private Long childId;

    @Column(name = "sponsor_id", updatable = false, nullable = false)
    private Long sponsorId;

    @Column(name = "sponsored_date", updatable = false, nullable = false)
    private Date sponsoredDate;

    @Column(name = "donation_duration", nullable = false)
    @Basic
    private String donationDurationText;

    @Transient
    private DonationDuration donationDuration;

    @Column(name = "deletion_date")
    @Temporal(TemporalType.DATE)
    private Date deletionDate;

    @PrePersist
    void onCreate() {
        if (donationDuration != null) {
            this.donationDurationText = donationDuration.getDonationDuration();
        }
    }

    public Long getChildSponsorAssocId() {
        return childSponsorAssocId;
    }

    public void setChildSponsorAssocId(Long childSponsorAssocId) {
        this.childSponsorAssocId = childSponsorAssocId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Date getSponsoredDate() {
        return sponsoredDate;
    }

    public void setSponsoredDate(Date sponsoredDate) {
        this.sponsoredDate = sponsoredDate;
    }

    public DonationDuration getDonationDuration() { return donationDuration; }

    public void setDonationDuration(DonationDuration donationDuration) { this.donationDuration = donationDuration; }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
    }
}
