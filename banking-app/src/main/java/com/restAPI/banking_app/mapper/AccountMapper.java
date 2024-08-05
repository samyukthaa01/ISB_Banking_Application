package com.restAPI.banking_app.mapper;

import com.restAPI.banking_app.dto.AccountDto;
import com.restAPI.banking_app.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto) {
//        return new Account(
//                accountDto.getId(),
//                accountDto.getAccountHolderName(),
//                accountDto.getBalance()

        return Account.builder()
                .id(accountDto.getId())
                .accountHolderName(accountDto.getAccountHolderName())
                .balance(accountDto.getBalance())
                .build();
    }

    public static AccountDto mapToAccountDto(Account account) {
        return  AccountDto.builder()
                .id(account.getId())
                .accountHolderName(account.getAccountHolderName())
                .balance(account.getBalance())
                .build();

    }
}
