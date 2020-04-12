package com.tucklets.app.controllers;

import com.tucklets.app.containers.ChildDetailsContainer;
import com.tucklets.app.containers.SponsorChildrenContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.ChildAdditionalDetail;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.PaymentMethod;
import com.tucklets.app.services.ChildAdditionalDetailService;
import com.tucklets.app.services.ChildService;
import com.tucklets.app.services.SponsorService;
import com.tucklets.app.utils.CalculationUtils;
import com.tucklets.app.utils.ContainerUtils;
import com.tucklets.app.utils.S3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/sponsor-a-child")
public class SponsorChildrenController {

    @Autowired
    ChildService childService;

    @Autowired
    SponsorService sponsorService;

    @Autowired
    ChildAdditionalDetailService childAdditionalDetailService;

    @GetMapping(value = "/")
    public String fetchAvailableChildren(Model model) {
        List<ChildDetailsContainer> children = fetchChildrenWithDetails();
        SponsorChildrenContainer sponsorChildrenContainer =
            new SponsorChildrenContainer(children);

        model.addAttribute("sponsorChildrenContainer", sponsorChildrenContainer);
        model.addAttribute("localeContainer", ContainerUtils.createLocaleContainer());
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

    /**
     * Fetches all available children that needs sponsorship and calculates the age.
     * Returns as a List<ChildDetailsContainer>().
     */
    public List<ChildDetailsContainer> fetchChildrenWithDetails() {
        var children = childService.fetchAvailableChildren();
        List<ChildDetailsContainer> childContainerList = new ArrayList<>();

        if (Objects.nonNull(children)) {
            for (Child child : children) {
                ChildDetailsContainer childDetailsContainer = new ChildDetailsContainer();
                var age = CalculationUtils.calculateAge(child.getBirthYear());
                childDetailsContainer.setAge(age);
                childDetailsContainer.setChild(child);
                // Fetch image location.
                ChildAdditionalDetail additionalDetails =
                    childAdditionalDetailService.fetchChildAdditionalDetailById(child.getChildId());
                childDetailsContainer.setChildImageLocation(S3Utils.computeS3Key(additionalDetails.getImageLocation()));
                childContainerList.add(childDetailsContainer);
            }
        }
        return childContainerList;
    }
}
