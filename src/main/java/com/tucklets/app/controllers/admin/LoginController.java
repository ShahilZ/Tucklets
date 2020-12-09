package com.tucklets.app.controllers.admin;

import com.tucklets.app.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping(path = "/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping(value = "/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @PostMapping(path = "/verify-login")
    public String verifyLogin(@RequestParam String username, @RequestParam String password) {
        return loginService.isValidAdmin(username, password) ? "redirect:admin/children-dashboard/" : "unauthorized";
    }

    @GetMapping(value = "/admin/")
    public String adminLogin() {
        return "redirect:children-dashboard/";
    }

}

