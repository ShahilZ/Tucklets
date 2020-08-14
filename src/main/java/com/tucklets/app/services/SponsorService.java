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

    public void addSponsor(Sponsor sponsor) { sponsorRepository.save(sponsor); }

    /**
     * Soft deletes the child with the given id.
     */
    public void deleteSponsor(Long sponsorId) {
        Optional<Sponsor> sponsor = sponsorRepository.fetchSponsorById(sponsorId);
        sponsor.ifPresent(value -> value.setDeletionDate(new Date()));
        sponsorRepository.save(sponsor.get());
    }
}

