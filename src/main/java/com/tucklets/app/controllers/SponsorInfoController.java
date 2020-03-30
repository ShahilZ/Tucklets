package com.tucklets.app.controllers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationFrequency;
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

@Controller
@RequestMapping(value = "/sponsor-info")
public class SponsorInfoController {

    @Autowired
    SponsorService sponsorService;

    @Autowired
    ChildService childService;

    @GetMapping(value = "/")
    public ModelAndView handleChildSelection(@RequestParam(value = "childId") String[] childrenIds) {
        ModelAndView modelAndView = new ModelAndView("sponsor-info");


        List<Child> children = new ArrayList<>();

        // If ids exist in the Child table, retrieve the objects.
        for (String id : childrenIds) {
            Long childId = Long.valueOf(id);
            Child child = childService.fetchChildById(childId);
            if (child != null) {
                children.add(child);
            }
        }

        modelAndView.addObject("donationFrequencies", DonationFrequency.getAllDonationFrequencies());
        modelAndView.addObject("sponsor", new Sponsor());
        return modelAndView;
    }

    @PostMapping(value = "/submit")
    public String handleSponsorSubmission(@ModelAttribute Sponsor sponsor) {

        // TODO: Validate form.
        sponsorService.addSponsor(sponsor);

        return "success";
    }


}
