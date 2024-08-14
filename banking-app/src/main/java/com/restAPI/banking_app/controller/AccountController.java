package com.restAPI.banking_app.controller;

import com.restAPI.banking_app.dto.*;
import com.restAPI.banking_app.service.AccountService;
import com.restAPI.banking_app.utils.AccountUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account management APIs")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Add Account REST API
    @Operation(
            summary = "Create New Account for Users",
            description = "Creating an account for a new user and assigning an account ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<BankResponseDto> addAccount(@RequestBody @Valid UserDto userDto) {
        BankResponseDto response = accountService.createAccount(userDto);
        HttpStatus status = response.getResponseCode().equals(AccountUtils.ACCOUNT_CREATION_SUCCESS) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @Operation(
            summary = "Balance Enquiry",
            description = "Given an account number, check the balance on the account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 201 SUCCESS"
    )

    @GetMapping("balanceEnquiry")
    public BankResponseDto balanceEnquiry(@RequestBody @Valid EnquiryRequestDto request) {
        return accountService.balanceEnquiry(request);
    }

    @GetMapping("nameEnquiry")
    public String nameEnquiry(@RequestBody @Valid EnquiryRequestDto request) {
        return accountService.nameEnquiry(request);
    }
    /*
     * @param
     *
     * */

    /**
     * Retrieves an account by its ID.
     * This method fetches an account from the database using the provided ID.
     * It utilizes the `accountService` to perform the retrieval and returns the
     * corresponding `AccountDto` object wrapped in a `ResponseEntity`. The
     * response status code is set to 200 (OK) if the account is found,
     * otherwise an appropriate error response is returned.
     *
     * @return A `ResponseEntity` containing the retrieved `AccountDto` object
     * on success, or an appropriate error response otherwise.
     */

    //Deposit REST API
    @PostMapping("/deposit")
    public ResponseEntity<BankResponseDto> deposit(
            @RequestBody @Valid DepositDto depositDto
    ) {
        BankResponseDto response = accountService.deposit(depositDto.getAccountNumber(), depositDto);
        return ResponseEntity.ok(response);
    }

    //Withdrawal
    @PostMapping("/withdrawal")
    public ResponseEntity<BankResponseDto> withdrawal(
            @RequestBody @Valid WithdrawalDto withdrawalDto
    ) {
        BankResponseDto response = accountService.withdrawal(withdrawalDto.getAccountNumber(), withdrawalDto);
        return ResponseEntity.ok(response);
    }

    //Transfer from one account to another
    @PostMapping("transfer")
    public BankResponseDto transfer(@RequestBody TransferRequestDto request) {
        return accountService.transfer(request);
    }

    @PostMapping("/login")
    public BankResponseDto login(@RequestBody LoginDto loginDto){
        return accountService.login(loginDto);
    }

    //Get all accounts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    //Delete account REST API
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok("Account deleted successfully!");
    }
}

