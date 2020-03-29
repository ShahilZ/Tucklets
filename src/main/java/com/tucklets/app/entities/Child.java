package com.tucklets.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "Child")
/**
 * Class representing the child. Columns should be self-explanatory.
 */
public class Child {
    @Id
    @SequenceGenerator(name = "child_generator", initialValue = 2000, allocationSize = 1)
    @GeneratedValue(generator = "child_generator")
    @Column(name = "childId", updatable = false, nullable = false)
    private Long childId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "grade", nullable = false)
    private int grade;

    @Column(name = "is_sponsored")
    private boolean isSponsored;

    @Column(name = "desired_occupation", nullable = false)
    private String desiredOccupation;

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
    }

    @PreUpdate
    void onUpdate() {
        this.setLastUpdateDate(new Date());
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isSponsored() {
        return isSponsored;
    }

    public void setSponsored(boolean sponsored) {
        isSponsored = sponsored;
    }

    public String getDesiredOccupation() {
        return desiredOccupation;
    }

    public void setDesiredOccupation(String desiredOccupation) {
        this.desiredOccupation = desiredOccupation;
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
