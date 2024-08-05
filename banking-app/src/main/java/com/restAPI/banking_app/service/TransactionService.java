package com.restAPI.banking_app.service;

import com.restAPI.banking_app.dto.TransactionDto;
import com.restAPI.banking_app.entity.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
