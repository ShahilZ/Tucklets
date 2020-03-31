package com.tucklets.app.services;

import com.tucklets.app.containers.ChildContainer;
import com.tucklets.app.db.repositories.ChildRepository;
import com.tucklets.app.entities.Child;
import com.tucklets.app.utils.CalculationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class ChildService {

    @Autowired
    ChildRepository childRepository;

    /**
     * Adds a child to the database.
     */
    public void addChild(Child child) { childRepository.save(child); }

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
     * Fetches all available children that needs sponsorship and calculates the age.
     * Returns as a List<ChildContainer>().
     */
    public List<ChildContainer> fetchAvailableChildrenWithAge() {
        var children = childRepository.fetchAvailableChildren();
        List<ChildContainer> childContainerList = new ArrayList<>();

        if (Objects.nonNull(children)) {
            for (Child child : children) {
                ChildContainer childContainer = new ChildContainer();
                var age = CalculationUtils.calculateAge(child.getBirthYear());
                childContainer.setAge(age);
                childContainer.setChild(child);
                childContainerList.add(childContainer);
            }
        }
        return childContainerList;
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
    public List<Child> fetchAllChildren() {
        return childRepository.fetchAllChildren();
    }

    /**
     * Removes all children in the db. This should only be used for testing.
     */
    public void resetChildDb() {
        childRepository.deleteAllChildren();
    }
}
