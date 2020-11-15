package com.tucklets.app.controllers;

import com.tucklets.app.entities.Admin;
import com.tucklets.app.services.AdminService;
import com.tucklets.app.services.ChildService;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
//        return "redirect:/admin/children-dashboard/";
        return "home";
    }

    // TODO: Remove this method after we figure a way to create default admin.
    @RequestMapping("/info/resetAdmins")
    public String resetAdmins() {
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
        return "success";
    }


    /**
     * Health check endpoint for AWS.
     */
    @GetMapping("/health")
    public String healthCheck() {
        return "success";
    }
}
