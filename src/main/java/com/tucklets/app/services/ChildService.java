package com.tucklets.app.services;

import com.tucklets.app.db.repositories.ChildRepository;
import com.tucklets.app.entities.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ChildService {

    @Autowired
    ChildRepository childRepository;

    /**
     * Adds a child to the database.
     */
    public Child addChild(Child child) { return childRepository.save(child); }

    /**
     * Adds a list of children to the db.
     */
    public void addMultipleChildren(List<Child> children) {
        childRepository.saveAll(children);
    }

    /**
     * Sets isSponsored on Child table
     */
    public void setSponsoredChildren(List<Child> children) {
        if (Objects.nonNull(children)) {
            for (Child child : children) {
                child.setSponsored(true);
                childRepository.save(child);
            }
        }
    }

    /**
     * Fetches all available children that need to be sponsored.
     */
    public List<Child> fetchAvailableChildren() {
        return childRepository.fetchAvailableChildren();
    }

    /**
     * Fetches a single child by the child's id.
     */
    public Child fetchChildById(Long childId) {
        Optional<Child> possibleChild = childRepository.fetchChild(childId);
        return possibleChild.orElse(null);
    }

    /**
     * Fetches all children from a given list of child's id.
     */
    public List<Child> fetchChildByIds(String[] childrenIds) {
        List<Child>children = new ArrayList<>();
        for (String id : childrenIds) {
            Long childId = Long.valueOf(id);
            Child possibleChild = childRepository.fetchChild(childId).orElse(null);
            if (Objects.nonNull(possibleChild)) {
                children.add(possibleChild);
            }
        }
        return children;
    }

    /**
     * Fetches all the children from the database.
     */
    public List<Child> fetchAllActiveChildren() {
        return childRepository.fetchAllActiveChildren();
    }

    /**
     * Returns the child with the given name and birth year if one exists in the database.
     * This will check the child's first and last names, along with the child's birth year.
     */
    public Child fetchChildByNameAndBirthYear(String firstName, String lastName, int birthYear) {
        return childRepository.fetchChildByNameAndBirthYear(firstName, lastName, birthYear).orElse(null);
    }

    /**
     * Soft deletes the child with the given id.
     */
    public void deleteChild(long childId) {
        Optional<Child> child = childRepository.fetchChild(childId);
        child.ifPresent(value -> value.setDeletionDate(new Date()));
        childRepository.save(child.get());
    }

    /**
     * Removes the Child with childId from the database.
     */
    public void removeChild(long childId) {
        childRepository.deleteById(childId);
    }


    /**
     * Removes all children in the db. This should only be used for testing.
     */
    public void resetChildDb() {
        childRepository.deleteAllChildren();
    }
}
