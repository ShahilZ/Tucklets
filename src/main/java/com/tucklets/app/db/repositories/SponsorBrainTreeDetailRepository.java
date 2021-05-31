package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.SponsorBrainTreeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorBrainTreeDetailRepository extends CrudRepository<SponsorBrainTreeDetail, Long>, JpaRepository<SponsorBrainTreeDetail, Long>{
}
