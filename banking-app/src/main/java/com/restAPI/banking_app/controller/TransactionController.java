package com.restAPI.banking_app.controller;

import com.itextpdf.text.DocumentException;
import com.restAPI.banking_app.entity.Transaction;
import com.restAPI.banking_app.service.impl.BankStatementImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/bankStatement")
@AllArgsConstructor

public class TransactionController {

    private BankStatementImpl bankStatement;
    /**
     * Generates a bank statement for the specified account number within the given date range.
     *
     * <p>This method accepts the account number, start date, and end date as query parameters
     * and returns a list of transactions that fall within that period. The generated statement
     * is retrieved using the BankStatementImpl service.
     *
     * @param accountNumber the account number for which the bank statement is to be generated.
     * @param startDate the start date of the period for which transactions are to be included in the statement.
     * @param endDate the end date of the period for which transactions are to be included in the statement.
     * @return a list of transactions within the specified date range for the given account number.
     * @throws DocumentException if there is an error in generating the document (e.g., PDF).
     * @throws FileNotFoundException if the file required for generating the statement is not found.
     */

   @GetMapping
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate) throws DocumentException, FileNotFoundException {
        return bankStatement.generateStatement(accountNumber, startDate, endDate);
    }
}