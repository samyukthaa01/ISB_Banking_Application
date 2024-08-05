package com.restAPI.banking_app.repository;

import com.restAPI.banking_app.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<Cards, Long> {

}
