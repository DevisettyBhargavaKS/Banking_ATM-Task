package com.Code.Banking.service;

import com.Code.Banking.model.Account;
import com.Code.Banking.model.Transaction;
import com.Code.Banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Account account, double creditAmount, double debitAmount, double balance) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setCreditAmount(creditAmount);
        transaction.setDebitAmount(debitAmount);
        transaction.setBalance(balance);
        transaction.setUpdatedAt(LocalDateTime.now());
        System.out.println(transaction.getUpdatedAt());
        return transactionRepository.save(transaction);

    }

    public Transaction updateTransaction(Transaction transaction) {
        transaction.setUpdatedAt(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
