package com.tucklets.app.controllers.admin;


import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationDuration;
import com.tucklets.app.entities.enums.PaymentMethod;
import com.tucklets.app.services.SponsorService;
import com.tucklets.app.utils.ContainerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/sponsor-dashboard")
public class SponsorDashboardController {

    @Autowired
    SponsorService sponsorService;

    @GetMapping("/")
    public String viewSponsors(Model model) {
        List<Sponsor> sponsors = sponsorService.fetchAllSponsors();
        model.addAttribute("sponsors", sponsors);
        model.addAttribute("localeContainer", ContainerUtils.createLocaleContainer());
        return "admin/sponsor-dashboard";
    }

    @DeleteMapping("/sponsor/remove-sponsor")
    public String removeChild(@RequestParam("sponsorId") Long sponsorId) {
        sponsorService.deleteSponsor(sponsorId);
        return "success";
    }

    @GetMapping(value = "/sponsor/edit-sponsor")
    public String retrieveEditSponsorInfo(@RequestParam(value = "sponsorId") String id,  Model model) {
        Long sponsorId = Long.valueOf(id);
        Sponsor sponsor = sponsorService.fetchSponsorById(sponsorId);
        model.addAttribute("sponsor", sponsor);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "admin/modify-sponsor-modal :: modify-sponsor-modal";
    }

    @PostMapping(value = "/sponsor/submit-sponsor")
    public String submitSponsorModifications(@ModelAttribute Sponsor sponsor) {
        sponsorService.addSponsor(sponsor);
        return "redirect:/admin/sponsor-dashboard";
    }
}