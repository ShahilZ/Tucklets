package com.tucklets.app.containers;

import com.tucklets.app.entities.Newsletter;

import java.util.List;

public class NewslettersContainer {

    private List<Newsletter> newsletters;

    public NewslettersContainer(){};

    public NewslettersContainer(List<Newsletter> newsletters) {
        this.newsletters = newsletters;
    }

    public List<Newsletter> getNewsletters() {
        return newsletters;
    }
}
