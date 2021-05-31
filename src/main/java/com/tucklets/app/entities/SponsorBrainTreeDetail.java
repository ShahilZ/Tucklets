package com.tucklets.app.entities;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sponsor_braintree_detail")
/**
 * Table representing brainTree customerIds and subscription id.
 */
public class SponsorBrainTreeDetail {
    @Id
    @SequenceGenerator(name = "sponsor_braintree_detail_generator", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(generator = "sponsor_braintree_detail_generator")
    @Column(name = "sponsor_braintree_detail_id", updatable = false, nullable = false)
    private Long sponsorBrainTreeDetailId;

    @Column(name = "sponsor_id", nullable = false)
    private Long sponsorId;

    @Column(name = "braintree_customer_id")
    private String brainTreeCustomerId;

    @Column(name = "braintree_subscription_id")
    private String brainTreeSubscriptionId;

    @Column(name = "archive_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date archiveDate;

    public SponsorBrainTreeDetail() {};

    public Long getSponsorBrainTreeDetailId() {
        return sponsorBrainTreeDetailId;
    }

    public void setSponsorBrainTreeDetailId(Long sponsorBrainTreeDetailId) {
        this.sponsorBrainTreeDetailId = sponsorBrainTreeDetailId;
    }

    public Long getSponsorId() { return sponsorId; }

    public void setSponsorId(Long sponsorId) { this.sponsorId = sponsorId; }

    public String getBrainTreeCustomerId() { return brainTreeCustomerId; }

    public void setBrainTreeCustomerId(String brainTreeCustomerId) {
        this.brainTreeCustomerId = brainTreeCustomerId;
    }

    public String getBrainTreeSubscriptionId() { return brainTreeSubscriptionId; }

    public void setBrainTreeSubscriptionId(String brainTreeSubscriptionId) {
        this.brainTreeSubscriptionId = brainTreeSubscriptionId;
    }

    public Date getArchiveDate() { return archiveDate; }

    public void setArchiveDate(Date archiveDate) { this.archiveDate = archiveDate; }
}
