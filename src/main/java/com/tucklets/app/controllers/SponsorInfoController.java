package com.tucklets.app.controllers;

import com.tucklets.app.containers.ChildAndSponsorContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.services.AmountService;
import com.tucklets.app.services.ChildAndSponsorAssociationService;
import com.tucklets.app.services.ChildService;
import com.tucklets.app.services.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/sponsor-info")
public class SponsorInfoController {

    @Autowired
    SponsorService sponsorService;

    @Autowired
    ChildAndSponsorAssociationService childAndSponsorAssociationService;

    @Autowired
    ChildService childService;

    @Autowired
    AmountService amountService;
    
    @GetMapping(value = "/")
    public ModelAndView handleChildSelection(@RequestParam(value = "childId") String[] childrenIds) {

        ModelAndView modelAndView = new ModelAndView("sponsor-info");

        var selectedChildren = childService.fetchChildByIds(childrenIds);
        ChildAndSponsorContainer childAndSponsor =
                createChildAndSponsorContainer(selectedChildren, new Sponsor());

        modelAndView.addObject("donationDuration", DonationDuration.getAllDonationDurations());
        modelAndView.addObject("childAndSponsor", childAndSponsor);
        return modelAndView;
    }

    @PostMapping(value = "/submit")
    public String handleSponsorSubmission(@ModelAttribute ChildAndSponsorContainer childAndSponsorContainer) {

        // TODO: Validate form.

        List<Child> selectedChildren = childAndSponsorContainer.getChildren();
        Sponsor sponsor = childAndSponsorContainer.getSponsor();
        DonationDuration donationDuration = childAndSponsorContainer.getDonationDuration();

        sponsorService.addSponsor(sponsor);
        childAndSponsorAssociationService.createAssociation(selectedChildren, sponsor, donationDuration);
        childService.setSponsoredChildren(selectedChildren);

        return "success";
    }

    /**
     * Creates the container of Children and Sponsor info for the frontend (sponsor-info page).
     */
    private ChildAndSponsorContainer createChildAndSponsorContainer(List<Child> children, Sponsor sponsor) {
        ChildAndSponsorContainer childAndSponsorContainer = new ChildAndSponsorContainer();

        var totalDonationAmount = amountService.computeTotalDonationAmount(children);
        sponsor.setDonationAmount(totalDonationAmount);

        childAndSponsorContainer.setSponsor(sponsor);
        childAndSponsorContainer.setChildren(children);
        childAndSponsorContainer.setNumChildren(children.size());
        return childAndSponsorContainer;
    }
}
