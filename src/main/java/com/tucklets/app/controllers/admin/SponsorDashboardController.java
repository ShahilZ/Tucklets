package com.tucklets.app.controllers.admin;


import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.PaymentMethod;
import com.tucklets.app.services.ManageSponsorService;
import com.tucklets.app.services.SponsorService;
import com.tucklets.app.utils.ContainerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/sponsor-dashboard")
public class SponsorDashboardController {

    @Autowired
    SponsorService sponsorService;

    @Autowired
    ManageSponsorService manageSponsorService;

    @GetMapping("/")
    public String viewSponsors(Model model) {
        List<Sponsor> sponsors = sponsorService.fetchAllSponsors();
        model.addAttribute("sponsors", sponsors);
        model.addAttribute("localeContainer", ContainerUtils.createLocaleContainer());
        return "admin/sponsor-dashboard";
    }

    @DeleteMapping("/remove-sponsor")
    public String removeChild(@RequestParam("email") String email) {
        sponsorService.deleteSponsor(email);
        return "success";
    }

    /**
     * This endpoint is to retrieve an empty sponsor object to be passed to the Sponsor Modal.
     */
    @GetMapping(value = "/add-sponsor")
    public String retrieveAddSponsorInfo(Model model) {
        Sponsor sponsor = new Sponsor();
        model.addAttribute("sponsor", sponsor);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "admin/sponsor-modal :: modify-sponsor-modal";
    }

    @GetMapping(value = "/edit-sponsor")
    public String retrieveEditSponsorInfo(@RequestParam(value = "sponsorId") String id,  Model model) {
        Long sponsorId = Long.valueOf(id);
        Sponsor sponsor = sponsorService.fetchSponsorById(sponsorId);
        model.addAttribute("sponsor", sponsor);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "admin/sponsor-modal :: modify-sponsor-modal";
    }

    @PostMapping(value = "/submit-sponsor")
    public String submitSponsorModifications(@ModelAttribute Sponsor sponsor) throws IOException {
        manageSponsorService.updateSponsor((Sponsor)sponsor);
        return "redirect:/admin/sponsor-dashboard/";
    }
}