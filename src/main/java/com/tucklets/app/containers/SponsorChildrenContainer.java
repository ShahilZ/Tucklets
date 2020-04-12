package com.tucklets.app.containers;

import java.util.List;

public class SponsorChildrenContainer {

    /**
     * List of children that are available for sponsorship.
     */
    private List<ChildDetailsContainer> children;

    public SponsorChildrenContainer(List<ChildDetailsContainer> children)
    {
        this.children = children;

    }

    public List<ChildDetailsContainer> getChildren() {
        return children;
    }

}
