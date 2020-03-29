package com.tucklets.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("sponsor")
public class SponsorChildrenController {

    @GetMapping(value = "/")
    public String home() {
        return "sponsor";
    }
}
