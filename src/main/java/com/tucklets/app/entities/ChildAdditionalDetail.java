package com.tucklets.app.entities;

import javax.persistence.*;


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
}