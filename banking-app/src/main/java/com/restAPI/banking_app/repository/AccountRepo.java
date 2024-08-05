package com.restAPI.banking_app.repository;

import com.restAPI.banking_app.entity.Account;
import com.restAPI.banking_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    Boolean existsByAccountNumber(String accountNumber);
    Account findByAccountNumber(String accountNumber);
    @Query(value = "SELECT a FROM Account a WHERE a.accountHolderName = ?1")
    Account findByAccountHolderName(String accountHolderName);
}
