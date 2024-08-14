package com.restAPI.banking_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Account number cannot be blank.")
    @Size(min = 12, max = 12, message = "Account number must be 12 digits long.")
    @Pattern(regexp = "\\d+", message = "Account number must contain only digits.")
    private String sourceAccountNumber;
    @NotBlank(message = "Account number cannot be blank.")
    @Size(min = 12, max = 12, message = "Account number must be 12 digits long.")
    @Pattern(regexp = "\\d+", message = "Account number must contain only digits.")
    private String destinationAccountNumber;
    @NotNull(message = "The amount cannot be null")
    private BigDecimal amount;
}
