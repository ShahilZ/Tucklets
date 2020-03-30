package com.tucklets.app.containers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;

import java.util.ArrayList;
import java.util.List;

public class ChildAndSponsor {

    Sponsor sponsor;

    List<String> childrenIds;

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    List<Child> children;

    public List<String> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(List<String> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }
}
