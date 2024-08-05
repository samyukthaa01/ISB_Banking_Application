package com.restAPI.banking_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositDto {

    @NotBlank(message = "Account number cannot be blank.")
    @Size(min = 12, max = 12, message = "Account number must be 12 digits long.")
    @Pattern(regexp = "\\d+", message = "Account number must contain only digits.")
    private String accountNumber;
    @NotNull(message = "The amount cannot be null")
    private double amount;
}
