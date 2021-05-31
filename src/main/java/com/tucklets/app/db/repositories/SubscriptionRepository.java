package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long>, JpaRepository<Subscription, Long>{

}
