package com.tucklets.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sponsor")
public class SponsorChildrenController {

    @RequestMapping(value = "/")
    public String home() {
        return "Hello, World!";
    }
}
