package com.eticaret.userservice.controller;

import com.eticaret.userservice.model.User;
import com.eticaret.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<String> getUserString(){

        return ResponseEntity.ok("Hi User");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

}
