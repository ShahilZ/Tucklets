package com.tucklets.app.controllers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.services.ChildService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sponsor-a-child")
public class SponsorChildrenController {

    @Autowired
    ChildService childService;

    @GetMapping(value = "/")
    public String home() {
        return "sponsor";
    }

    @RequestMapping(value = "/add")
    public void addChild() {
        var test = new Child();
        test.setAge(7);
        test.setDesiredOccupation("soldier");
        test.setFirstName("bob");
        test.setLastName("Cloud");
        test.setGrade(4);
        childService.addChild(test);
    }

}
