package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.SponsorAndDonationAssoc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SponsorAndDonationAssociationRepository extends CrudRepository<SponsorAndDonationAssoc, Long> { }
