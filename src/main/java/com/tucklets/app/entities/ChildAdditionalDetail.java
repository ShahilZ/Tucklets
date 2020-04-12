package com.tucklets.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Entity
@Table(name = "Child_Additional_Detail")
public class ChildAdditionalDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "child_additional_detail_id", updatable = false, nullable = false)
    private Long childAdditionalDetailId;

    @Column(name = "child_id", nullable = false)
    private Long childId;

    @Column(name = "image_location", nullable = false)
    private String imageLocation;

    @Column(name = "archived_date")
    @Temporal(TemporalType.DATE)
    private Date archivedDate;

    public Long getChildAdditionalDetailId() {
        return childAdditionalDetailId;
    }

    public void setChildAdditionalDetailId(Long childAdditionalDetailId) {
        this.childAdditionalDetailId = childAdditionalDetailId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Date getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(Date archivedDate) {
        this.archivedDate = archivedDate;
    }
}