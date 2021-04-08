package com.tucklets.app.controllers;

import com.braintreegateway.*;
import com.google.gson.Gson;
import com.tucklets.app.configs.AppConfig;
import com.tucklets.app.containers.BrainTreePaymentContainer;
import com.tucklets.app.containers.SponsorshipContainer;
import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.db.repositories.SponsorRepository;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.SponsorAddress;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.entities.enums.SponsorInfoStatus;
import com.tucklets.app.entities.enums.State;
import com.tucklets.app.services.*;
import com.tucklets.app.utils.TextUtils;
import com.tucklets.app.validations.DonationValidator;
import com.tucklets.app.validations.SponsorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/sponsor")
public class SponsorController {

    private static final Gson GSON = new Gson();

    @Autowired
    SponsorService sponsorService;

    @Autowired
    ChildService childService;

    @Autowired
    AmountService amountService;

    @Autowired
    ManageChildrenService manageChildrenService;

    @GetMapping(value = "/")
    public String handleSponsorInfoLanding() {
        // Redirect to root page.
        return "home";
    }

    @GetMapping(value = "/selections/")
    @ResponseBody
    public String handleChildSelection(@RequestParam(value = "childIds") String[] childrenIds) {
        // TODO: Validate request params?
        SponsorshipContainer result = sponsorService.associateSelectedChildren(childrenIds);
        return GSON.toJson(result);
    }

    @PostMapping(value = "/submit")
    @ResponseBody
    public ResponseEntity<String> handleSponsorSubmission(@RequestBody SponsorshipContainer sponsorshipContainer) {

        BrainTreePaymentContainer brainTreePaymentContainer = sponsorshipContainer.getBrainTreePaymentContainer();
        Sponsor sponsor = sponsorshipContainer.getSponsor();
        Donation donation = sponsorshipContainer.getDonation();
        List<ChildDetailsContainer> childDetailsContainers = sponsorshipContainer.getChildren();
        var result = sponsorService.processSponsorship(brainTreePaymentContainer, sponsor, donation, childDetailsContainers);
        if (result.getStatus() != SponsorInfoStatus.SUCCESS) {
            return ResponseEntity.badRequest().body(result.getErrors());
        }
        return ResponseEntity.ok(result.getStatus().toString());



    }
}
