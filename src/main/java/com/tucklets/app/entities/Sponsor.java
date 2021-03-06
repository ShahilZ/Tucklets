package com.tucklets.app.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "Sponsor",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email", name = "uniqueEmaileConstraint")}
)
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

    @Column(name = "address")
    private SponsorAddress address;

    @Column(name = "subscribed", nullable = false)
    private boolean subscribed;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "last_update_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lastUpdateDate;

    @Column(name = "deletion_date")
    @Temporal(TemporalType.DATE)
    private Date deletionDate;

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

    public SponsorAddress getAddress() {
        return address;
    }

    public void setAddress(SponsorAddress address) {
        this.address = address;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Date getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
    }
}