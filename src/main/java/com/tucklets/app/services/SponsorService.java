package com.tucklets.app.services;

import com.tucklets.app.db.repositories.SponsorRepository;
import com.tucklets.app.entities.Sponsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SponsorService {

    @Autowired
    SponsorRepository sponsorRepository;

    @Autowired
    ManageSponsorService manageSponsorService;

    /**
     * Fetches all active sponsors
     */
    public List<Sponsor> fetchAllSponsors() {
        return sponsorRepository.fetchAllSponsors();
    }

    /**
     * Fetches all active sponsors who are subscribed to receive notifications.
     */
    public List<Sponsor> fetchAllSubscribedSponsors() {
        return sponsorRepository.fetchSubscribedSponsors();
    }

    public Sponsor fetchSponsorById(Long sponsorId) {
        Optional<Sponsor> possibleSponsor = sponsorRepository.fetchSponsorById(sponsorId);
        return possibleSponsor.orElse(null);
    }

    /**
     * Returns Optional<Sponnsor> based on the provided email.
     */
    public Optional<Sponsor> fetchSponsorByEmail(String email) {
        return sponsorRepository.fetchSponsorByEmail(email);
    }

    public void addSponsor(Sponsor sponsor) {
        Date today = new Date();
        sponsor.setLastUpdateDate(today);
        Optional<Sponsor> existingSponsorOptional = sponsorRepository.fetchSponsorByEmail(sponsor.getEmail());
        // Set creationDate if sponsor is not existing one.
        if (existingSponsorOptional.isEmpty()) {
            sponsor.setCreationDate(today);
        }
        else {
            // Set existing fields since this is an existing sponsor.
            manageSponsorService.addExistingFieldsToSponsor(sponsor, existingSponsorOptional.get());
        }
        // Create/update sponsor.
        sponsorRepository.save(sponsor);
    }

    /**
     * Soft deletes the child with the given id.
     */
    public void deleteSponsor(String email) {
        Optional<Sponsor> sponsor = sponsorRepository.fetchSponsorByEmail(email);
        sponsor.ifPresent(value -> value.setDeletionDate(new Date()));
        sponsorRepository.save(sponsor.get());
    }

    /**
     * Set subscribed column to false. Should be called with another method to validate sponosr.
     */
    private void unsubscribeSponsorNewsletter(Sponsor sponsor) {
        sponsor.setSubscribed(false);
        sponsorRepository.save(sponsor);
    }

    /**
     * Unsubscribes a sponsor from receiving newsletters. Do nothing if email does not correspond
     * to a sponsor in our db. This is to avoid giving too much info to the user.
     */
    public void unsubscribeSponsorFromNewsletter(String email) {
        Optional<Sponsor> sponsorOptional = sponsorRepository.fetchSponsorByEmail(email);
        if (sponsorOptional.isPresent()) {
            unsubscribeSponsorNewsletter(sponsorOptional.get());
        }
    }
}

