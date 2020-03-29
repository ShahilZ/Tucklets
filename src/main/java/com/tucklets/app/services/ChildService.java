package com.tucklets.app.services;

import com.tucklets.app.db.repositories.ChildRepository;
import com.tucklets.app.entities.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService {

    @Autowired
    ChildRepository childRepository;

    public void addChild(Child child) {
        childRepository.save(child);
    }
}
