package com.Code.Banking.controller;

import com.Code.Banking.model.Transaction;
import com.Code.Banking.service.AccountService;
import com.Code.Banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin
public class TransactionController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/history/{accountId}")
    public ResponseEntity<Object> getTransactionHistory(@PathVariable Long accountId) {
        List<Transaction> transactionHistory = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok().body(transactionHistory);
    }
}
