package com.Code.Banking.controller;

import com.Code.Banking.model.Account;
import com.Code.Banking.response.APIResponse;
import com.Code.Banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public APIResponse createAccount(@RequestBody Account account) {
        String message = "";
        APIResponse apiResponse = new APIResponse(HttpStatus.BAD_REQUEST.value(), null, message, null);
        Account savedAccount = null;
        try {
            savedAccount = accountService.createAccount(account);
        } catch (Exception e) {
            message = e.getMessage();
            apiResponse.setMessage(message);
        }
        if (savedAccount != null) {
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData(savedAccount);
            apiResponse.setMessage(message);
        }
        return apiResponse;
    }

    @GetMapping("/{id}")
    public APIResponse getAccount(@PathVariable Long id) {
        String message = "";
        APIResponse apiResponse = new APIResponse(HttpStatus.BAD_REQUEST.value(), null, message, null);
        Optional<Account> getAccount = null;
        try {
            getAccount = accountService.getAccount(id);
        } catch (Exception e) {
            message = e.getMessage();
            apiResponse.setMessage(message);
        }
        if (getAccount != null) {
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData(getAccount);
            apiResponse.setMessage(message);
        }
        return apiResponse;
    }

    @PostMapping("/{id}/credit")
    public APIResponse credit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        String message = "";
        APIResponse apiResponse = new APIResponse(HttpStatus.BAD_REQUEST.value(), null, null, null);
        try {
            Double amount = request.get("amount");
            if (amount != null) {
                Account creditedAccount = accountService.credit(id, amount);
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setData(creditedAccount);
                apiResponse.setMessage(message);
//                message = "Amount credited successfully";
            } else {
//                message = "Amount is missing in the request";
            }
        } catch (RuntimeException e) {
            message = e.getMessage();
            apiResponse.setMessage(message);
        }
        return apiResponse;
    }

    @PostMapping("/{id}/debit")
    public APIResponse debit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        String message = "";
        APIResponse apiResponse = new APIResponse(HttpStatus.BAD_REQUEST.value(), null, null, null);
        try {
            Double amount = request.get("amount");
            if (amount != null) {
                Account debitedAccount = accountService.debit(id, amount);
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setData(debitedAccount);
                apiResponse.setMessage(message);
            }
        } catch (Exception e) {
            message = e.getMessage();
            apiResponse.setMessage(message);
        }
        return apiResponse;
    }
}
