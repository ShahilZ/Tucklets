package com.tucklets.app.services;

import com.tucklets.app.db.repositories.SponsorRepository;
import com.tucklets.app.entities.Sponsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ManageSponsorService {

    @Autowired
    SponsorRepository sponsorRepository;

    /**
     * Handles the updates to the given sponsor object.
     */
    public void updateSponsor(Sponsor sponsor) {
        Optional<Sponsor> existingSponsorOptional = sponsorRepository.fetchSponsorByEmail(sponsor.getEmail());
        // if existingSponsor is not null, then we are updating
        existingSponsorOptional.ifPresent(value -> this.addExistingFieldsToSponsor(sponsor, value));
        //update the sponsor
        sponsorRepository.save(sponsor);
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


