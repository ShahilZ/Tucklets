package com.tucklets.app.containers.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.ChildAndSponsorAssoc;
import org.springframework.web.multipart.MultipartFile;

/**
 * Container class that includes all "child-related" information.
 */
public class ChildDetailsContainer {

    private Child child;
    private String childImageLocation;
    private ChildAndSponsorAssoc childAndSponsorAssoc;
    private MultipartFile childImageFile;
    private int age;

    public ChildDetailsContainer(){};

    @JsonCreator
    public ChildDetailsContainer (Integer childId ) {
        this.child = new Child(Long.valueOf(childId));
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getChildImageLocation() {
        return childImageLocation;
    }

    public void setChildImageLocation(String childImageLocation) {
        this.childImageLocation = childImageLocation;
    }

    public ChildAndSponsorAssoc getChildAndSponsorAssoc() {
        return childAndSponsorAssoc;
    }

    public void setChildAndSponsorAssoc(ChildAndSponsorAssoc childAndSponsorAssoc) {
        this.childAndSponsorAssoc = childAndSponsorAssoc;
    }

    public MultipartFile getChildImageFile() {
        return childImageFile;
    }

    public void setChildImageFile(MultipartFile childImageFile) {
        this.childImageFile = childImageFile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
