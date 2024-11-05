package com.restAPI.banking_app.service.impl;

import com.restAPI.banking_app.ExceptionHandling.ApiException;
import com.restAPI.banking_app.dto.*;
import com.restAPI.banking_app.entity.Account;
import com.restAPI.banking_app.entity.Role;
import com.restAPI.banking_app.entity.User;
import com.restAPI.banking_app.repository.AccountRepo;
import com.restAPI.banking_app.repository.UserRepo;
import com.restAPI.banking_app.service.AccountService;
import com.restAPI.banking_app.service.EmailService;
import com.restAPI.banking_app.service.TransactionService;
import com.restAPI.banking_app.utils.AccountUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    /*@Autowired
    JwtTokenProvider jwtTokenProvider;*/
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private AccountRepo accountRepository;

    @Override
    @Transactional
    public BankResponseDto createAccount(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return BankResponseDto.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage((AccountUtils.ACCOUNT_EXISTS_MESSAGE))
                    .accountDto(null)
                    .build();
        }
        User newUser = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .status("ACTIVE")
                .role(Role.valueOf("ROLE_ADMIN"))
                .build();
        User savedUser = userRepository.save(newUser);

        // Generate a unique account number
        String accountNumber = AccountUtils.generateAccountNumber();


        // Create and save the Account entity
        Account newAccount = Account.builder()
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO) // Assuming initial balance is 0
                .accountHolderName(savedUser.getFirstName() + " " + savedUser.getLastName())
                .user(savedUser)// Associate the account with the created user
                .build();
        //newAccount = accountRepository.save(newAccount);
        Account savedAccount = accountRepository.save(newAccount);
        //Send email alert
        EmailDto emailDto = EmailDto.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Thank You! Your account has been successfully created.\nYour Account Details:\n" +
                        "Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + "\nAccount Number: " + savedAccount.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDto);

        //Create and return the response Dto
        return BankResponseDto.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountDto(AccountDto.builder()
                        .id(savedAccount.getId())
                        .accountHolderName(savedAccount.getAccountHolderName())
                        .balance(savedAccount.getBalance())
                        .accountNumber(savedAccount.getAccountNumber())
                        .build())
                .build();

    }

    public BankResponseDto login(LoginDto loginDto) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        // Optionally set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT Token
        String token = generateJwtToken(authentication);

        // Send login alert email
        EmailDto loginAlert = EmailDto.builder()
                .subject("You're logged in!")
                .recipient(loginDto.getEmail())
                .messageBody("You logged into your account. If you did not perform this action, please contact your bank for further assistance")
                .build();
        emailService.sendEmailAlert(loginAlert);

        return BankResponseDto.builder()
                .responseMessage("Login Successful")
                .token(token)
                .build();
    }

    private String generateJwtToken(Authentication authentication) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .claim("roles", authentication.getAuthorities())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public BankResponseDto balanceEnquiry(EnquiryRequestDto request) {
        //check if provided account number exists in the db
        if (!accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new ApiException(AccountUtils.ACCOUNT_NOT_EXIST, AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
        }
        Account foundAccount = accountRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponseDto.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountDto(AccountDto.builder()
                        .id(foundAccount.getId())
                        .balance(foundAccount.getBalance())
                        .accountNumber(foundAccount.getAccountNumber())
                        .accountHolderName(foundAccount.getAccountHolderName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequestDto request) {
        if (!accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new ApiException(AccountUtils.ACCOUNT_NOT_EXIST, AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
        }
        Account foundAccount = accountRepository.findByAccountNumber(request.getAccountNumber());
        return foundAccount.getAccountHolderName();
    }

    @Override
    public BankResponseDto deposit(String accountNumber, DepositDto depositDto) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new ApiException(AccountUtils.ACCOUNT_NOT_EXIST, AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
        }

        account.setBalance(account.getBalance().add(BigDecimal.valueOf(depositDto.getAmount())));
        accountRepository.save(account);

        //Save transaction
        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber((account.getAccountNumber()))
                .transactionType("DEPOSIT")
                .amount(BigDecimal.valueOf(depositDto.getAmount()))
                .build();

        transactionService.saveTransaction(transactionDto);

        return BankResponseDto.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                .accountDto(AccountDto.builder()
                        .id(account.getId())
                        .balance(account.getBalance())
                        .accountNumber(account.getAccountNumber())
                        .accountHolderName(account.getAccountHolderName())
                        .build())
                .build();
    }

    @Override
    public BankResponseDto withdrawal(String accountNumber, WithdrawalDto withdrawalDto) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new ApiException(AccountUtils.ACCOUNT_NOT_EXIST, AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
        }

        if (account.getBalance().compareTo(BigDecimal.valueOf(withdrawalDto.getAmount())) < 0) {
            throw new ApiException(AccountUtils.INSUFFICIENT_BALANCE_CODE, AccountUtils.INSUFFICIENT_BALANCE_MESSAGE);
        }


        account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(withdrawalDto.getAmount())));
        accountRepository.save(account);

        //Save transaction
        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber((account.getAccountNumber()))
                .transactionType("WITHDRAWAL")
                .amount(BigDecimal.valueOf(withdrawalDto.getAmount()))
                .build();

        transactionService.saveTransaction(transactionDto);

        return BankResponseDto.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountDto(AccountDto.builder()
                        .id(account.getId())
                        .balance(account.getBalance())
                        .accountNumber(account.getAccountNumber())
                        .accountHolderName(account.getAccountHolderName())
                        .build())
                .build();
    }

    @Override
    public BankResponseDto transfer(TransferRequestDto request) {
        boolean isSourceAccountExist = accountRepository.existsByAccountNumber(request.getSourceAccountNumber());
        boolean isDestinationAccountExist = accountRepository.existsByAccountNumber(request.getDestinationAccountNumber());

        if (!isDestinationAccountExist) {
            return BankResponseDto.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountDto(null)
                    .build();
        }

        Account sourceAccountUser = accountRepository.findByAccountNumber(request.getSourceAccountNumber());
        if (request.getAmount().compareTo(sourceAccountUser.getBalance()) > 0) {
            return BankResponseDto.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountDto(null)
                    .build();
        }

        // Debit the source account
        sourceAccountUser.setBalance(sourceAccountUser.getBalance().subtract(request.getAmount()));
        User sourceUser = sourceAccountUser.getUser();
        String sourceUsername = sourceUser.getFirstName() + " " + sourceUser.getLastName();

        accountRepository.save(sourceAccountUser);

        EmailDto debitAlert = EmailDto.builder()
                .subject("DEBIT ALERT")
                .recipient(sourceUser.getEmail())
                .messageBody("The sum of " + request.getAmount() + " has been deducted from your account! Your current balance is " + sourceAccountUser.getBalance())
                .build();

        emailService.sendEmailAlert(debitAlert);

        // Credit the destination account
        Account destinationAccountUser = accountRepository.findByAccountNumber(request.getDestinationAccountNumber());
        destinationAccountUser.setBalance(destinationAccountUser.getBalance().add(request.getAmount()));
        User destinationUser = destinationAccountUser.getUser();
        String recipientUsername = destinationUser.getFirstName() + " " + destinationUser.getLastName();

        accountRepository.save(destinationAccountUser);

        EmailDto creditAlert = EmailDto.builder()
                .subject("CREDIT ALERT")
                .recipient(destinationUser.getEmail()) // Send to recipient's email
                .messageBody("The sum of " + request.getAmount() + " has been credited to your account from " + sourceUsername + ". Your current balance is " + destinationAccountUser.getBalance())
                .build();

        emailService.sendEmailAlert(creditAlert);

        // Save transaction
        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber(destinationAccountUser.getAccountNumber())
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionDto);

        return BankResponseDto.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountDto(null)
                .build();
    }


    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(account -> AccountDto.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .accountNumber(account.getAccountNumber())
                .accountHolderName(account.getAccountHolderName())
                .build()).collect(Collectors.toList());
    }

    public void deleteAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account != null) {
            accountRepository.delete(account);
        }
    }
    /*public List<TransactionDto> getTransactionsByAccountNumber(String accountNumber) {
        return accountRepository.findTransactionsByAccountNumber(accountNumber);
    }*/
    @Override
    public BankResponseDto getTransactionsByAccountNumber(TransactionDto request) {
        //check if provided account number exists in the db
        if (!accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new ApiException(AccountUtils.ACCOUNT_NOT_EXIST, AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
        }
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .build();

        return BankResponseDto.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountDto(null)
                .build();

    }


    // lambda expression.
    // Optional
    // stream api

}
