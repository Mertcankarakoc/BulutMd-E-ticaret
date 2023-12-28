package com.eticaret.userservice.repository;

import com.eticaret.userservice.model.Role;
import com.eticaret.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);

    Optional<User> findByUsername(String username);
}
