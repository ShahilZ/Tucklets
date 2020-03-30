package com.tucklets.app.controllers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BasicController {

    @Autowired
    ChildService childService;

    @RequestMapping("/")
    public String init() {
        childService.resetChildDb();
        for (int i=0; i < 10; i++) {
            var child = new Child();
            child.setAge(i + 2);
            child.setDesiredOccupation(i % 2 == 0 ?"Magician" : "Software Engineer");
            child.setFirstName(String.format("Phil %d", i));
            child.setLastName("Dunphy");
            child.setGrade(i + 1);
            childService.addChild(child);
        }

        return "success";
    }
}
