package com.restAPI.banking_app.service;

import com.restAPI.banking_app.dto.EmailDto;

public interface EmailService {
    void sendEmailAlert(EmailDto emailDto);
    void sendEmailWithAttachment(EmailDto emailDto);
}
