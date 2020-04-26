package com.tucklets.app.containers;

import com.tucklets.app.containers.admin.ChildDetailsContainer;

import java.util.List;

public class SponsorAChildContainer {

    /**
     * List of children that are available for sponsorship.
     */
    private List<ChildDetailsContainer> children;

    public SponsorAChildContainer(List<ChildDetailsContainer> children)
    {
        this.children = children;
    }

    public List<ChildDetailsContainer> getChildren() {
        return children;
    }
}
