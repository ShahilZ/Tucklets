package com.tucklets.app.controllers.admin;

import com.google.gson.Gson;
import com.tucklets.app.containers.NewslettersContainer;
import com.tucklets.app.containers.admin.NewsletterStatusContainer;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.services.NewsletterService;
import com.tucklets.app.services.SponsorService;
import com.tucklets.app.utils.ContainerUtils;
import com.tucklets.app.utils.NewsletterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/newsletters")
public class NewsletterController {

    private static final Gson GSON = new Gson();

    @Autowired
    NewsletterService newslettersService;

    @Autowired
    SponsorService sponsorService;

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

    @DeleteMapping("/remove-newsletter")
    public String removeNewsletter(@RequestParam("newsletterId") Long newsletterId) {
        newslettersService.deleteNewsletter(newsletterId);
        return "success";
    }

    @PutMapping ("/email-latest")
    @ResponseBody
    public String emailLatestNewsletter()
        throws IOException, MessagingException
    {
        // Get all subscribed sponsors.
        List<Sponsor> subscribedSponsors = sponsorService.fetchAllSubscribedSponsors();
        NewsletterStatusContainer newsletterStatusContainer =
            newslettersService.emailLatestNewsLetter(subscribedSponsors);
        return GSON.toJson(newsletterStatusContainer);
    }



}
