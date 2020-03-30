package com.tucklets.app.services;

import com.tucklets.app.db.repositories.ChildAndSponsorAssociationRepository;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.ChildAndSponsorAssoc;
import com.tucklets.app.entities.Sponsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ChildAndSponsorAssociationService {

    @Autowired
    ChildAndSponsorAssociationRepository childAndSponsorAssociationRepository;

    public void createAssociation(List<Child> children, Sponsor sponsor) {
        if (Objects.nonNull(children) && Objects.nonNull(sponsor)) {
            for (Child child : children) {
                ChildAndSponsorAssoc childAndSponsorAssoc = new ChildAndSponsorAssoc();
                childAndSponsorAssoc.setChildId(child.getChildId());
                childAndSponsorAssoc.setSponsorId(sponsor.getSponsorId());
                childAndSponsorAssoc.setSponsoredDate(new Date());
                childAndSponsorAssociationRepository.save(childAndSponsorAssoc);
            }
        }
    }
}