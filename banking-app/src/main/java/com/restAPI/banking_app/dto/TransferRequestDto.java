package com.restAPI.banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TransferRequestDto {
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal amount;
}
