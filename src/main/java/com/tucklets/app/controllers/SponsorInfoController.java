package com.tucklets.app.controllers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationFrequency;
import com.tucklets.app.containers.ChildAndSponsor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/sponsor-info")
public class SponsorInfoController {

    @Autowired
    SponsorService sponsorService;

    @Autowired
    ChildAndSponsorAssociationService childAndSponsorAssociationService;

    @Autowired
    ChildService childService;

    @GetMapping(value = "/")
    public ModelAndView handleChildSelection(@RequestParam(value = "childId") String[] childrenIds) {

        ModelAndView modelAndView = new ModelAndView("sponsor-info");
        List<Child> selectedChildren = new ArrayList<>();

        // If ids exist in the Child table, retrieve the objects.
        for (String id : childrenIds) {
            Long childId = Long.valueOf(id);
            Child child = childService.fetchChildById(childId);
            if (Objects.nonNull(child)) {
                selectedChildren.add(child);
            }
        }

        modelAndView.addObject("donationFrequencies", DonationFrequency.getAllDonationFrequencies());

        ChildAndSponsor childAndSponsor = new ChildAndSponsor();
        childAndSponsor.setChildren(selectedChildren);
        childAndSponsor.setSponsor(new Sponsor());
        modelAndView.addObject("childAndSponsor", childAndSponsor);

        return modelAndView;
    }

    @PostMapping(value = "/submit")
    public String handleSponsorSubmission(@ModelAttribute ChildAndSponsor childAndSponsor) {

        // TODO: Validate form.

        List<Child> selectedChildren = childAndSponsor.getChildren();
        Sponsor sponsor = childAndSponsor.getSponsor();

        // Add sponsor information to the Sponsor table
        sponsorService.addSponsor(sponsor);

        // Create entry in childAndSponsorAssoc table
        childAndSponsorAssociationService.createAssociation(selectedChildren, sponsor);

        //Set isSponsored = true for each child
        childService.setSponsoredChildren(selectedChildren);

        return "success";
    }
}
