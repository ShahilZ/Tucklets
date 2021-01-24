package com.tucklets.app.controllers;

import com.google.gson.Gson;
import com.tucklets.app.configs.AwsConfig;
import com.tucklets.app.containers.SponsorAChildContainer;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.services.DonationService;
import com.tucklets.app.services.ManageChildrenService;
import com.tucklets.app.services.NewsletterService;
import com.tucklets.app.utils.NewsletterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;


@RestController
@RequestMapping("/info")
public class InfoController {

    private static final Gson GSON = new Gson();

    @Autowired
    AwsConfig awsConfig;

    @Autowired
    NewsletterService newslettersService;

    @Autowired
    ManageChildrenService manageChildrenService;

    @Autowired
    DonationService donationService;

    @GetMapping("/fetchNewsletters")
    public String fetchNewsletters() {
        return GSON.toJson(NewsletterUtils.createNewslettersContainer(newslettersService, awsConfig));
    }

    @GetMapping(value = "/fetchChildren")
    public String fetchAvailableChildren() {
        SponsorAChildContainer sponsorAChildContainer =
            new SponsorAChildContainer(manageChildrenService.fetchChildrenWithDetails());
       return GSON.toJson(sponsorAChildContainer);
    }

    @GetMapping(value = "/changeDonationDuration")
    public Map<String, String> changeDonationDuration(
            @RequestParam(value = "amount") String donationAmount,
            @RequestParam(value = "donationDuration") String desiredDuration,
            @RequestParam(value = "prevDuration") String prevDuration)
    {
        DonationDuration newDuration = DonationDuration.of(desiredDuration);
        DonationDuration previousDuration = DonationDuration.of(prevDuration);
        BigDecimal currentAmount = BigDecimal.valueOf(Long.parseLong(donationAmount));
        return donationService.changeDonationDuration(newDuration, previousDuration, currentAmount);
    }
}
