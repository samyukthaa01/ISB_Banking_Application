package com.restAPI.banking_app.service;

import com.restAPI.banking_app.dto.CardDto;

public interface CardService {

    CardDto issueCard(CardDto cardDto);
    CardDto updateCard(Long id, CardDto cardDto);
    void removeCard(Long id);
    CardDto retrieveCard(Long id);
}
