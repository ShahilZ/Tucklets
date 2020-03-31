package com.tucklets.app.containers;

import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.entities.enums.DonationDuration;

import java.util.List;

public class ChildContainer {

    Child child;
    int age;

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }
}