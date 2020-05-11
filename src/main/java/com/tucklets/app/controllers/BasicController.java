package com.tucklets.app.controllers;

import com.tucklets.app.entities.Admin;
import com.tucklets.app.services.AdminService;
import com.tucklets.app.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BasicController {

    @Autowired
    AdminService adminService;

    @Autowired
    ChildService childService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String init() {
        adminService.resetAdmins();
        // Encrypt password
        String hashedPassword = passwordEncoder.encode("password");
        System.out.println(hashedPassword);

        // Initialize admin.
        Admin admin = new Admin();
        admin.setEncryptedPassword(hashedPassword);
        admin.setUsername("admin");
        admin.setEnabled(true);
        adminService.saveAdmin(admin);
        return "redirect:/admin/children-dashboard/";
    }

    /**
     * Health check endpoint for AWS.
     */
    @GetMapping("/health")
    public String healthCheck() {
        return "success";
    }
}
