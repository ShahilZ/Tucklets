package com.tucklets.app.controllers;

import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationFrequency;
import com.tucklets.app.services.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/sponsor-info")
public class SponsorInfoController {

    @Autowired
    SponsorService sponsorService;

    @GetMapping(value = "/")
    public ModelAndView handleChildSelection() {
        ModelAndView modelAndView = new ModelAndView("sponsor-info");
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
