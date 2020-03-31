package com.tucklets.app.controllers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    ChildService childService;

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        List<Child> children = childService.fetchAllChildren();
        model.addAttribute("children", children);
        return "admin/dashboard";
    }

    @GetMapping("/dashboard/upload")
    public String upload() {
        return "success";
    }

}
