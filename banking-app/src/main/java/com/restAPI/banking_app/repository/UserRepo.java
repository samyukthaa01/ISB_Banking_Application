package com.restAPI.banking_app.repository;

import com.restAPI.banking_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
Boolean existsByEmail(String email);

}
