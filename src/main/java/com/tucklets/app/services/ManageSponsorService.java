package com.tucklets.app.services;

import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.db.repositories.ChildRepository;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.ChildAdditionalDetail;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.utils.CalculationUtils;
import com.tucklets.app.utils.Constants;
import com.tucklets.app.utils.S3Utils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ManageSponsorService {

    @Autowired
    SponsorService sponsorService;

    /**
     * Handles the updates to the given sponsor object.
     */
    public void updateSponsor(Sponsor sponsor) throws IOException {
        Sponsor existingSponsor = sponsorService.fetchSponsorById(sponsor.getSponsorId());
        // if existingSponsor is not null, then we are updating
        if (existingSponsor != null) {
            this.addExistingFieldsToSponsor(sponsor, existingSponsor);
        }
        //update the sponsor
        sponsorService.addSponsor(sponsor);
    }

    /**
     * Sets any additional fields for the imported Sponsor object that is taken from his or her existing counterpart.
     * This method will port over items like sponsorId and creationDate.
     */
    public void addExistingFieldsToSponsor(Sponsor sponsor, Sponsor existingSponsor) {
        sponsor.setSponsorId(existingSponsor.getSponsorId());
        sponsor.setCreationDate(existingSponsor.getCreationDate());
        sponsor.setDeletionDate(existingSponsor.getDeletionDate());
    }
}


