package com.restAPI.banking_app.controller;

import com.restAPI.banking_app.dto.CardDto;
import com.restAPI.banking_app.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")

public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    //Issue new card
    @PostMapping
    public ResponseEntity<CardDto> issueCard(@RequestBody CardDto cardDto) {
        return new ResponseEntity<>(cardService.issueCard(cardDto), HttpStatus.CREATED);
    }

    //Retrieve card details
    @GetMapping("/{id}")
    public ResponseEntity<CardDto> retrieveCard(@PathVariable Long id) {
        CardDto cardDto = cardService.retrieveCard(id);
        return ResponseEntity.ok(cardDto);
    }

    //Update card details
    @PutMapping("/{id}")
    public ResponseEntity<CardDto> updateCard(@PathVariable Long id, @RequestBody CardDto cardDto) {
        CardDto updatedCard = cardService.updateCard(id, cardDto);
        return ResponseEntity.ok(updatedCard);
    }

    //Remove a card
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCard(@PathVariable Long id) {
        cardService.removeCard(id);
        return ResponseEntity.ok("Card deleted successfully!");
    }

}
