package com.restAPI.banking_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
@Schema(
        name = "User AccountName"
)
    @NotEmpty(message = "Account holder name can't be left empty")
    private String accountHolderName;
    @Schema(
            name = "User Account Balance"
    )
    @Min(value=10, message = "Minimum balance should be RM10")
    private BigDecimal balance;
    @Schema(
            name = "User Account Number"
    )
    @NotBlank(message = "Account number cannot be blank.")
    @Size(min = 12, max = 12, message = "Account number must be 12 digits long.")
    @Pattern(regexp = "\\d+", message = "Account number must contain only digits.")
    private String accountNumber;
    //private long accountId
}
