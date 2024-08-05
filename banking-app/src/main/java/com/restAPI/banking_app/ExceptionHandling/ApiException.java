package com.restAPI.banking_app.ExceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiException extends RuntimeException {

    public ApiException(String message, String errorCode) {

        super(message);

    }
}
