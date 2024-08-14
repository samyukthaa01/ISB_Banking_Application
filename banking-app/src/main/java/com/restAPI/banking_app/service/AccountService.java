package com.restAPI.banking_app.service;

import com.restAPI.banking_app.dto.*;

import java.util.List;

public interface AccountService {
    BankResponseDto createAccount(UserDto userDto);
    BankResponseDto balanceEnquiry(EnquiryRequestDto request);
    String nameEnquiry(EnquiryRequestDto request);
    BankResponseDto deposit(String accountNumber, DepositDto depositDto);
    BankResponseDto withdrawal(String accountNumber, WithdrawalDto withdrawalDto);
    BankResponseDto transfer(TransferRequestDto request);
    BankResponseDto login(LoginDto loginDto);
    List<AccountDto> getAllAccounts();
    void deleteAccount(String accountNumber);

}
