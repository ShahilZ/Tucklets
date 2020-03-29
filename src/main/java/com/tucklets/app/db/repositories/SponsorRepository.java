package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.Sponsor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SponsorRepository extends CrudRepository<Sponsor, Long> {
}
