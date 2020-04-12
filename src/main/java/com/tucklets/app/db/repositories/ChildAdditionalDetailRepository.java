package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.ChildAdditionalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildAdditionalDetailRepository extends CrudRepository<ChildAdditionalDetail, Long>, JpaRepository<ChildAdditionalDetail, Long>{

    @Query("select cad from ChildAdditionalDetail cad where cad.childId = :childId and cad.archivedDate is null")
    Optional<ChildAdditionalDetail> fetchChildAdditionalDetailByChildId(@Param("childId") Long childId);
}
