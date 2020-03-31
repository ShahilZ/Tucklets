package com.tucklets.app.controllers;

import com.tucklets.app.entities.Admin;
import com.tucklets.app.entities.Child;
import com.tucklets.app.services.AdminService;
import com.tucklets.app.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
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
        childService.resetChildDb();
        adminService.resetAdmins();
        for (int i=0; i < 10; i++) {
            var child = new Child();
            child.setAge(i + 2);
            child.setDesiredOccupation(i % 2 == 0 ? "Magician" : "Software Engineer");
            child.setFirstName(String.format("Phil %d", i));
            child.setLastName("Dunphy");
            child.setGrade(i + 1);
            childService.addChild(child);
        }
        // Encrypt password
        String hashedPassword = passwordEncoder.encode("password");
        System.out.println(hashedPassword);

        // Initialize admin.
        Admin admin = new Admin();
        admin.setPassword(hashedPassword);
        admin.setUsername("admin");
        adminService.saveAdmin(admin);
        return "redirect:/admin/dashboard";
    }

}
