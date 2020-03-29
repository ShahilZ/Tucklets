package com.tucklets.app.services;

import com.tucklets.app.db.repositories.SponsorRepository;
import com.tucklets.app.entities.Sponsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SponsorService {

    @Autowired
    SponsorRepository sponsorRepository;

    public void addSponsor(Sponsor sponsor) { sponsorRepository.save(sponsor); }
}

