package com.tucklets.app.entities;

import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.entities.enums.PaymentMethod;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Donation")
/**
 * Table representing a donation. A sponsor can have multiple donations tied to him or her.
 */
public class Donation {
    @Id
    @SequenceGenerator(name = "donation_generator", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(generator = "donation_generator")
    @Column(name = "donation_id", updatable = false, nullable = false)
    private Long donationId;

    @Column(name = "donation_amount", updatable = false, nullable = false)
    private BigDecimal donationAmount;

    @Column(name = "donation_duration_text", updatable = false, nullable = false)
    private String donationDurationText;

    @Transient
    private DonationDuration donationDuration;

    @Column(name = "payment_method", updatable = false, nullable = false)
    @Basic
    private int paymentMethodValue;

    @Transient
    private PaymentMethod paymentMethod;

    @Column(name = "creation_date", updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "deletion_date")
    @Temporal(TemporalType.DATE)
    private Date deletionDate;

    @PrePersist
    void onCreate() {
        if (paymentMethod != null) {
            this.paymentMethodValue = paymentMethod.getPaymentMethodValue();
        }
        if (donationDuration != null) {
            this.donationDurationText = donationDuration.getDonationDuration();
        }
    }

    @PostLoad
    void fillTransient() {
        if (paymentMethodValue > 0) {
            this.paymentMethod = PaymentMethod.of(paymentMethodValue);
        }
        this.donationDuration = DonationDuration.of(donationDurationText);
    }

    public Donation() {};

    public Donation(BigDecimal donationAmount, DonationDuration donationDuration) {
        this.donationAmount = donationAmount;
        this.donationDuration = donationDuration;
    }

    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }

    public BigDecimal getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(BigDecimal donationAmount) {
        this.donationAmount = donationAmount;
    }

    public DonationDuration getDonationDuration() {
        return donationDuration;
    }

    public void setDonationDuration(DonationDuration donationDuration) {
        this.donationDuration = donationDuration;
    }

    public int getPaymentMethodValue() {
        return paymentMethodValue;
    }

    public void setPaymentMethodValue(int paymentMethodValue) {
        this.paymentMethodValue = paymentMethodValue;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
