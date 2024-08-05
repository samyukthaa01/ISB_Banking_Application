package com.restAPI.banking_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EmailDto {
    @NotBlank(message = "The recipient email cannot be blank")
    private String recipient;
    @NotEmpty(message = "The message cannot be empty")
    private String messageBody;
    private String subject;
    private String attachment;

}
