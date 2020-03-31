package com.tucklets.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/")
public class AdminController {

    @GetMapping(path = "/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        return "success";

    }

}
