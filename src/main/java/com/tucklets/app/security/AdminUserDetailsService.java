package com.tucklets.app.security;

import com.tucklets.app.entities.Admin;
import com.tucklets.app.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    AdminService adminService;

    public AdminUserDetailsService() {
        super();
    }


    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Admin admin = adminService.fetchAdminByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AdminUserDetails(admin);
    }
}
