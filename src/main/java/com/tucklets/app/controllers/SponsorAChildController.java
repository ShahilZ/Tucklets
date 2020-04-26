package com.tucklets.app.controllers;

import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.containers.SponsorAChildContainer;
import com.tucklets.app.services.ManageChildrenService;
import com.tucklets.app.utils.ContainerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sponsor-a-child")
public class SponsorAChildController {

    @Autowired
    ManageChildrenService manageChildrenService;

    @GetMapping(value = "/")
    public String fetchAvailableChildren(Model model) {
        List<ChildDetailsContainer> children = manageChildrenService.fetchChildrenWithDetails();
        SponsorAChildContainer sponsorAChildContainer = new SponsorAChildContainer(children);
        model.addAttribute("sponsorAChildContainer", sponsorAChildContainer);
        model.addAttribute("localeContainer", ContainerUtils.createLocaleContainer());
        return "sponsor-a-child";
    }
}
