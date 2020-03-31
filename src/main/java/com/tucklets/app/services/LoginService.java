package com.tucklets.app.services;

import com.tucklets.app.entities.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Validates user authenticity using BCrypt.
     */
    public boolean isValidAdmin(String username, String password) {
        // Check if username exists in db.
        Admin admin = adminService.fetchAdminByUsername(username);
        if (admin != null) {
            return passwordEncoder.matches(password, admin.getEncryptedPassword());
        }
        return false;
    }
}
