package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.ChildAndSponsorAssoc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChildAndSponsorAssociationRepository extends CrudRepository<ChildAndSponsorAssoc, Long> { }
