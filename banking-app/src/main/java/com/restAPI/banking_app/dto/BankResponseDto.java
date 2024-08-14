package com.restAPI.banking_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BankResponseDto {
    @Schema(
            name = "Response Code for Error"
    )
    private String responseCode;
    @Schema(
            name = "Response Message for The Response Code"
    )
    private String responseMessage;

    @Schema(
            name = "Token for the Authentication"
    )
    private String token;
    private AccountDto accountDto;
}
