package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends CrudRepository<Child, Long>, JpaRepository<Child, Long> {

    @Query("select c from Child c where c.sponsored = false")
    List<Child> fetchAvailableChildren();

    @Query("select c from Child c where c.childId = :childId")
    Optional<Child> fetchChild(@Param("childId") Long childId);

    @Query("select c from Child c where c.deletionDate is null")
    List<Child> fetchAllActiveChildren();

    @Query("select c from Child c where c.firstName = :firstName and c.lastName = :lastName and c.birthYear = :birthYear")
    Optional<Child> fetchChildByNameAndBirthYear(String firstName, String lastName, int birthYear);

    @Transactional
    @Modifying
    @Query("delete from Child")
    void deleteAllChildren();

}
