package com.restAPI.banking_app.service.impl;

import com.restAPI.banking_app.dto.CardDto;
import com.restAPI.banking_app.entity.Cards;
import com.restAPI.banking_app.repository.CardRepo;
import com.restAPI.banking_app.service.CardService;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepo cardRepo;

    public CardServiceImpl(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }


    @Override
    public CardDto issueCard(CardDto cardDto) {

        Cards cards = Cards.builder()
                .cardNumber(cardDto.getCardNumber())
                .expirationDate(cardDto.getExpirationDate())
                .cvv(cardDto.getCvv())
                .cardType(cardDto.getCardType())
                .build();

        // Save the Cards entity to the database
        cards = cardRepo.save(cards);


        return CardDto.builder()
                .card_id(cards.getCard_id())
                .cardNumber(cards.getCardNumber())
                .expirationDate(cards.getExpirationDate())
                .cvv(cards.getCvv())
                .cardType(cards.getCardType())
                .build();
    }

    @Override
    public CardDto updateCard(Long id, CardDto cardDto) {
        Cards existingCard = cardRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        existingCard.setCardNumber(cardDto.getCardNumber());
        existingCard.setExpirationDate(cardDto.getExpirationDate());
        existingCard.setCvv(cardDto.getCvv());
        existingCard.setCardType(cardDto.getCardType());

        existingCard = cardRepo.save(existingCard);

        return CardDto.builder()
                .card_id(existingCard.getCard_id())
                .cardNumber(existingCard.getCardNumber())
                .expirationDate(existingCard.getExpirationDate())
                .cvv(existingCard.getCvv())
                .cardType(existingCard.getCardType())
                .build();
    }

    @Override
    public void removeCard(Long id) {
        cardRepo
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Card does not exist"));

        cardRepo.deleteById(id);
    }

    @Override
    public CardDto retrieveCard(Long id) {
        Cards cards = cardRepo
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return CardDto.builder()
                .card_id(cards.getCard_id())
                .cardNumber(cards.getCardNumber())
                .expirationDate(cards.getExpirationDate())
                .cvv(cards.getCvv())
                .cardType(cards.getCardType())
                .build();
    }
}
