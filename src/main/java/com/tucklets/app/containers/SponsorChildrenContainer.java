package com.tucklets.app.containers;

import java.util.List;

public class SponsorChildrenContainer {

    /**
     * List of children that are available for sponsorship.
     */
    private List<ChildContainer> children;

    public SponsorChildrenContainer(List<ChildContainer> children)
    {
        this.children = children;

    }

    public List<ChildContainer> getChildren() {
        return children;
    }

}
