package com.tucklets.app.db.repositories;

import com.tucklets.app.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long>, JpaRepository<Admin, Long> {

    @Query("select a from Admin a where a.username = :username")
    Optional<Admin> fetchAdminByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("delete from Admin")
    void deleteAllAdmins();
}
