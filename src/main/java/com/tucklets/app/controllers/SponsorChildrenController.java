package com.tucklets.app.controllers;

import com.tucklets.app.containers.ChildContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.entities.enums.PaymentMethod;
import com.tucklets.app.services.ChildService;
import com.tucklets.app.services.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/sponsor-a-child")
public class SponsorChildrenController {

    @Autowired
    ChildService childService;

    @Autowired
    SponsorService sponsorService;

    @GetMapping(value = "/")
    public String fetchAvailableChildren(Model model) {
        List<ChildContainer> children = childService.fetchAvailableChildrenWithAge();

        model.addAttribute("children", children);
        return "sponsor-a-child";
    }

    @RequestMapping(value = "/add")
    public String addChild() {
        var test = new Child();
        test.setBirthYear(2010);
        test.setDesiredOccupation("soldier");
        test.setFirstName("bob");
        test.setLastName("Cloud");
        test.setGrade(4);
        childService.addChild(test);

        // returns the success.html page
        return "success";
    }

    @RequestMapping(value = "/cart")
    public String addSponsor() {
        var sponsor = new Sponsor();
        sponsor.setFirstName("Elma");
        sponsor.setLastName("Rod");
        sponsor.setChurchName("ChurchZome");
        sponsor.setEmail("cloudzeebytez@gmail.com");
        sponsor.setAddress("123 Fake Street San Diego, CA 92912");
        sponsor.setDonationAmount(new BigDecimal(1000));
        sponsor.setPaymentMethod(PaymentMethod.PAYPAL);
        sponsorService.addSponsor(sponsor);

        // returns the success.html page
        return "success";
    }
}
