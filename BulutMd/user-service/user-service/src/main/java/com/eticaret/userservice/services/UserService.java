package com.eticaret.userservice.services;

import com.eticaret.userservice.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
    User getUserByUsername(String username);
}
