package com.tucklets.app.entities;

import com.tucklets.app.entities.enums.PaymentMethod;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Sponsor")
/**
 * Class representing the sponsor of a child. Columns should be self-explanatory.
 */
public class Sponsor {

    @Id
    @SequenceGenerator(name = "sponsor_generator", initialValue = 4000, allocationSize = 1)
    @GeneratedValue(generator = "sponsor_generator")
    @Column(name = "sponsor_id", updatable = false, nullable = false)
    private Long sponsorId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "church_name")
    private String churchName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "donation_amount", nullable = false)
    private BigDecimal donationAmount;

    @Column(name = "payment_method", nullable = false)
    @Basic
    private int paymentMethodValue;

    @Transient
    private PaymentMethod paymentMethod;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "last_update_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lastUpdateDate;

    @PrePersist
    void onCreate() {
        Date today = new Date();
        this.setCreationDate(today);
        this.setLastUpdateDate(today);

        if (paymentMethod != null) {
            this.paymentMethodValue = paymentMethod.getPaymentMethodValue();
        }
    }

    @PostLoad
    void fillTransient() {
        if (paymentMethodValue > 0) {
            this.paymentMethod = PaymentMethod.of(paymentMethodValue);
        }
    }

    @PreUpdate
    void onUpdate() {
        this.setLastUpdateDate(new Date());
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(BigDecimal donationAmount) {
        this.donationAmount = donationAmount;
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

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}