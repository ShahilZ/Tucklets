package com.tucklets.app.controllers;

import com.google.gson.Gson;
import com.tucklets.app.containers.SponsorInfoContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.services.AmountService;
import com.tucklets.app.services.ChildAndSponsorAssociationService;
import com.tucklets.app.services.ChildService;
import com.tucklets.app.services.EmailService;
import com.tucklets.app.services.ManageChildrenService;
import com.tucklets.app.services.SponsorService;
import com.tucklets.app.utils.ContainerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
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
    AmountService amountService;

    @Autowired
    EmailService emailService;

    @Autowired
    ManageChildrenService manageChildrenService;

    @GetMapping(value = "/")
    public String handleChildSelection(@RequestParam(value = "childIds") String[] childrenIds) {

        var selectedChildren = childService.fetchChildByIds(childrenIds);
        var childrenDetailContainers = manageChildrenService.createChildDetailsContainers(selectedChildren);
        var totalDonationAmount = amountService.computeTotalDonationAmount(selectedChildren);
        Sponsor sponsor = new Sponsor();
        sponsor.setDonationAmount(totalDonationAmount);

        SponsorInfoContainer sponsorInfoContainer = new SponsorInfoContainer(
            sponsor,
            childrenDetailContainers,
            DonationDuration.getAllDonationDurations(),
            childrenIds,
            null,
            selectedChildren.size());
        sponsorInfoContainer.setNumChildren(selectedChildren.size());

        return GSON.toJson(sponsorInfoContainer);
    }

    @PostMapping(value = "/submit")
    public ModelAndView handleSponsorSubmission(@ModelAttribute SponsorInfoContainer sponsorInfoContainer) {

        // TODO: Validate form.

        Sponsor sponsor = sponsorInfoContainer.getSponsor();
        DonationDuration donationDuration = sponsorInfoContainer.getSelectedDonationDuration();
        List<Child> children = childService.fetchChildByIds(sponsorInfoContainer.getSelectedChildIds());
        sponsorService.addSponsor(sponsor);

        childAndSponsorAssociationService.createAssociation(children, sponsor, donationDuration);
        childService.setSponsoredChildren(children);

        emailService.sendConfirmationEmail(sponsor, children);

        ModelAndView modelAndView = new ModelAndView("thank-you");
        modelAndView.addObject("localeContainer", ContainerUtils.createLocaleContainer());

        return modelAndView;
    }
}
