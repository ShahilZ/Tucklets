package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.Child;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends CrudRepository<Child, Long> {

}
