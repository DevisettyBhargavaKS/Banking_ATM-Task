package com.Code.Banking.service;

import com.Code.Banking.model.Account;
import com.Code.Banking.model.Transaction;
import com.Code.Banking.repository.AccountRepository;
import com.Code.Banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionService transactionService;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    public Account credit(Long id, double amount) {
        Account account = getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        double previousBalance = account.getBalance();
        account.setBalance(previousBalance + amount);
        accountRepository.save(account);
        createTransaction(account, amount, 0, previousBalance + amount);
        return account;
    }

    public Account debit(Long id, double amount) {
        Account account = getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        double previousBalance = account.getBalance();
        account.setBalance(previousBalance - amount);
        accountRepository.save(account);
        createTransaction(account, 0, amount, previousBalance - amount);
        return account;
    }

    private void createTransactionIfNeeded(Account account, double creditAmount, double debitAmount, double balance) {
        Transaction existingTransaction = transactionRepository.findByAccountIdAndCreditAmountAndDebitAmountAndBalance(
                account.getId(), creditAmount, debitAmount, balance);

        if (existingTransaction != null) {
            existingTransaction.setBalance(balance);
            existingTransaction.setUpdatedAt(LocalDateTime.now());
            transactionService.updateTransaction(existingTransaction); // Update the transaction
        } else {
            createTransaction(account, creditAmount, debitAmount, balance);
        }
    }

    private void createTransaction(Account account, double creditAmount, double debitAmount, double balance) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setCreditAmount(creditAmount);
        transaction.setDebitAmount(debitAmount);
        transaction.setBalance(balance);
        transaction.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
}
