package com.tucklets.app.controllers;

import com.tucklets.app.services.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/unsubscribe")
public class UnsubscribeController {

    @Autowired
    SponsorService sponsorService;

    @GetMapping(value = "/")
    public String handleUnsubscribeLandingPage() {
        // Redirect to root page.
        return "home";
    }

    @PostMapping(value = "/submit")
    @ResponseBody
    public ResponseEntity<String> unsubscribe(@RequestBody String email) {
        sponsorService.unsubscribeSponsorFromNewsletter(email);
        return ResponseEntity.ok("Successfully unsubscribed.");
    }
}
