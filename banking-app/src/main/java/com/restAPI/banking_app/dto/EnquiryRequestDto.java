package com.restAPI.banking_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EnquiryRequestDto {
    @NotNull(message = "Account number cannot be null.")
    @Size(min = 12, max = 12, message = "Account number must be 12 digits long.")
    @Pattern(regexp = "\\d+", message = "Account number must contain only digits.")
    private String accountNumber;
}
