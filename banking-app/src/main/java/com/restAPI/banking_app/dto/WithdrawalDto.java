package com.restAPI.banking_app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class WithdrawalDto {
    @NotNull(message = "Account number cannot be null.")
    @Size(min = 12, max = 12, message = "Account number must be 12 digits long.")
    @Pattern(regexp = "\\d+", message = "Account number must contain only digits.")
    private String accountNumber;
    @NotNull(message = "The amount cannot be null")
    @Max(value = 10000, message = "Maximum withdrawal amount is RM10,000")
    private double amount;
}
