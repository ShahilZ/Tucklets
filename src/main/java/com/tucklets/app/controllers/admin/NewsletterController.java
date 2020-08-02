package com.tucklets.app.controllers.admin;

import com.tucklets.app.containers.NewslettersContainer;
import com.tucklets.app.services.NewsletterService;
import com.tucklets.app.utils.ContainerUtils;
import com.tucklets.app.utils.NewsletterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(value = "/admin/newsletters")
public class NewsletterController {

    @Autowired
    NewsletterService newslettersService;

    @GetMapping("/")
    public String viewNewsletters(Model model) {
        NewslettersContainer newslettersContainer = NewsletterUtils.createNewslettersContainer(newslettersService);
        model.addAttribute("newslettersContainer", newslettersContainer);
        model.addAttribute("localeContainer", ContainerUtils.createLocaleContainer());
        return "admin/manage-newsletters";
    }

    @PostMapping("/upload")
    public String uploadNewsletter(@RequestParam("file") MultipartFile newsletterData, Model model) throws IOException {
        // Verify pdf?
        newslettersService.uploadAndSaveNewsletter(newsletterData);
        return "redirect:/admin/newsletters/";
    }






}
