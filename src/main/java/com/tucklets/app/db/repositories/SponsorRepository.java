package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SponsorRepository extends CrudRepository<Sponsor, Long>, JpaRepository<Sponsor, Long> {

    @Query("select s from Sponsor s where s.deletionDate is null")
    List<Sponsor> fetchAllSponsors();

    @Query("select s from Sponsor s where s.sponsorId = :sponsorId")
    Optional<Sponsor> fetchSponsorById(@Param("sponsorId") Long sponsorId);
}
