package com.restAPI.banking_app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardDto {


    private long card_id;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String cardType;


}
