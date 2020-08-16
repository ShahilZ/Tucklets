package com.tucklets.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/thank-you")
    public String handleThankYou() {
        // Redirect back to the main page.
        return "redirect:/";
    }

}
