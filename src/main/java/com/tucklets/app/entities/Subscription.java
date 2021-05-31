package com.tucklets.app.entities;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Subscription")
/**
 * Table representing brainTree customerIds and subscription id.
 */
public class Subscription {
    @Id
    @SequenceGenerator(name = "subscription_generator", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(generator = "subscription_generator")
    @Column(name = "subscription_id", updatable = false, nullable = false)
    private Long subscriptionId;

    @Column(name = "sponsor_id", nullable = false)
    private Long sponsorId;

    @Column(name = "braintree_customer_id")
    private String brainTreeCustomerId;

    @Column(name = "braintree_subscription_id")
    private String brainTreeSubscriptionId;

    @Column(name = "archive_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date archiveDate;

    public Subscription() {};

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
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
