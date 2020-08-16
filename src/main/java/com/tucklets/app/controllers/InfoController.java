package com.tucklets.app.controllers;

import com.google.gson.Gson;
import com.tucklets.app.containers.SponsorAChildContainer;
import com.tucklets.app.services.ManageChildrenService;
import com.tucklets.app.services.NewsletterService;
import com.tucklets.app.utils.ContainerUtils;
import com.tucklets.app.utils.NewsletterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

    private static final Gson GSON = new Gson();

    @Autowired
    NewsletterService newslettersService;

    @Autowired
    ManageChildrenService manageChildrenService;

    @GetMapping("/locale")
    public String loadLocales() {
        return GSON.toJson(ContainerUtils.createLocaleContainer());
    }

    @GetMapping("/fetchNewsletters")
    public String fetchNewsletters() {
        return GSON.toJson(NewsletterUtils.createNewslettersContainer(newslettersService));
    }

    @GetMapping(value = "/fetchChildren")
    public String fetchAvailableChildren() {
        SponsorAChildContainer sponsorAChildContainer =
            new SponsorAChildContainer(manageChildrenService.fetchChildrenWithDetails());
       return GSON.toJson(sponsorAChildContainer);
    }
}
