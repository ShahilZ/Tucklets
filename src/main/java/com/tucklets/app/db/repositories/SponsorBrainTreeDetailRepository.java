package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.SponsorBrainTreeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SponsorBrainTreeDetailRepository extends CrudRepository<SponsorBrainTreeDetail, Long>, JpaRepository<SponsorBrainTreeDetail, Long>{

    @Query("select sbtd from SponsorBrainTreeDetail sbtd where sbtd.sponsorId = :sponsorId")
    Optional<SponsorBrainTreeDetail> findBySponsorId(@Param("sponsorId") long sponsorId);
}
