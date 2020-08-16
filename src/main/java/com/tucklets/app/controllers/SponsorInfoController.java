package com.tucklets.app.controllers;

import com.google.gson.Gson;
import com.tucklets.app.containers.SponsorInfoContainer;
import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.services.AmountService;
import com.tucklets.app.services.ChildAndSponsorAssociationService;
import com.tucklets.app.services.ChildService;
import com.tucklets.app.services.DonationService;
import com.tucklets.app.services.EmailService;
import com.tucklets.app.services.ManageChildrenService;
import com.tucklets.app.services.SponsorAndDonationAssociationService;
import com.tucklets.app.services.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/sponsor-info")
public class SponsorInfoController {

    private static final Gson GSON = new Gson();

    @Autowired
    SponsorService sponsorService;

    @Autowired
    ChildAndSponsorAssociationService childAndSponsorAssociationService;

    @Autowired
    ChildService childService;

    @Autowired
    DonationService donationService;

    @Autowired
    AmountService amountService;

    @Autowired
    EmailService emailService;

    @Autowired
    ManageChildrenService manageChildrenService;

    @Autowired
    SponsorAndDonationAssociationService sponsorAndDonationAssociationService;

    @GetMapping(value = "/")
    public String handleSponsorInfoLanding() {
        // Redirect to root page.
        return "home";
    }

    @GetMapping(value = "/selections/")
    @ResponseBody
    public String handleChildSelection(@RequestParam(value = "childIds") String[] childrenIds) {
        // TODO: Validate request params?
        Long[] childIds = Arrays.stream(childrenIds).map(Long::parseLong).toArray(Long[]::new);

        var selectedChildren = childService.fetchChildByIds(childIds);
        var childrenDetailContainers = manageChildrenService.createChildDetailsContainers(selectedChildren);
        var totalDonationAmount = amountService.computeTotalDonationAmount(selectedChildren);
        Sponsor sponsor = new Sponsor();
        Donation donation = new Donation(totalDonationAmount);

        SponsorInfoContainer sponsorInfoContainer = new SponsorInfoContainer(
            donation, sponsor, childrenDetailContainers);
        return GSON.toJson(sponsorInfoContainer);
    }

    @PostMapping(value = "/submit")
    @ResponseBody
    public String handleSponsorSubmission(@RequestBody SponsorInfoContainer sponsorInfoContainer) {

        // TODO: Validate form.
        Sponsor sponsor = sponsorInfoContainer.getSponsor();
        Donation donation = sponsorInfoContainer.getDonation();
        DonationDuration donationDuration =
            sponsorInfoContainer.getDonation().getDonationDuration() == null
                ? DonationDuration.ONE_YEAR
                : DonationDuration.INDEFINITE;

        // Add donation info.
        donationService.addDonation(donation);
        // Add sponsor info.
        sponsorService.addSponsor(sponsor);

        sponsorAndDonationAssociationService.createAssociation(sponsor, donation);
        List<ChildDetailsContainer> childrenContainer = sponsorInfoContainer.getChildren();


        // SponsorInfoContainer contains selected children, then create children assocs and send email regarding them.
        if (!childrenContainer.isEmpty()) {
            Long[] childIds = childrenContainer
                .stream()
                .map(childDetailsContainer -> childDetailsContainer.getChild().getChildId())
                .toArray(Long[]::new);
            List<Child> children = childService.fetchChildByIds(childIds);
            childAndSponsorAssociationService.createAssociation(children, sponsor, donationDuration);
            childService.setSponsoredChildren(children);
            emailService.sendConfirmationEmail(sponsor, children, donation);

        }
        else {
            // TODO: Generic sponsorship flow; send different email.
        }

        return "success";
    }
}
