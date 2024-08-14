package com.restAPI.banking_app.repository;

import com.restAPI.banking_app.entity.Account;
import com.restAPI.banking_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
Boolean existsByEmail(String email);
Optional<User> findByEmail(String email);
}
