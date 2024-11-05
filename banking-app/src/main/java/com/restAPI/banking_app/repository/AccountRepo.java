package com.restAPI.banking_app.repository;

import com.restAPI.banking_app.dto.TransactionDto;
import com.restAPI.banking_app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    Boolean existsByAccountNumber(String accountNumber);

    Account findByAccountNumber(String accountNumber);

    @Query("SELECT t FROM Transaction t WHERE t.account.accountNumber = ?1 AND t.amount >= 500")
    List<TransactionDto> findTransactionsByAccountNumber(String accountNumber);
}
