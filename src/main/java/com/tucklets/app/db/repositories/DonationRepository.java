package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends CrudRepository<Donation, Long>, JpaRepository<Donation, Long>{

}
