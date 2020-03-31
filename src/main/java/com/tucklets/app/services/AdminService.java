package com.tucklets.app.services;

import com.tucklets.app.db.repositories.AdminRepository;
import com.tucklets.app.entities.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    /**
     * Saves admin into the db.
     */
    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    /**
     * Fetches the admin from the db with the given username.
     */
    public Admin fetchAdminByUsername(String username) {
        return adminRepository.fetchAdminByUsername(username).orElse(null);
    }

    /**
     * Removes all admins from the db.
     */
    public void resetAdmins() {
        adminRepository.deleteAllAdmins();
    }
}
