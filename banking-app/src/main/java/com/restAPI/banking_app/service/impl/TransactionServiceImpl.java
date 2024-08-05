package com.restAPI.banking_app.service.impl;

import com.restAPI.banking_app.dto.TransactionDto;
import com.restAPI.banking_app.entity.Transaction;
import com.restAPI.banking_app.repository.CardRepo;
import com.restAPI.banking_app.repository.TransactionRepo;
import com.restAPI.banking_app.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }


    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("SUCCESS")
                .build();
        transactionRepo.save(transaction);
        System.out.println("Transaction saved successfully");
    }
}
