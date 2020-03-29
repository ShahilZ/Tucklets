package com.tucklets.app.controllers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sponsor")
public class SponsorChildrenController {

    @Autowired
    ChildService childService;

    @RequestMapping(value = "/")
    public String home() {
        return "Hello, World!";
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
