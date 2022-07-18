package com.ayoub.bankaccount.controllers;

import com.ayoub.bankaccount.dtos.AccountDTO;
import com.ayoub.bankaccount.dtos.RestOperationDTO;
import com.ayoub.bankaccount.exceptions.AccountNotFoundException;
import com.ayoub.bankaccount.exceptions.BalanceNotSufficientException;
import com.ayoub.bankaccount.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Account Rest API
 * @author mkharbach
 * @since 1.0.0
 */
@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
@CrossOrigin("*")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountNumber}")
    public AccountDTO getAccount(@PathVariable(name = "accountNumber") long accountNumber) {
        try{
            return accountService.getAccount(accountNumber);
        }catch (AccountNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with account number : " + accountNumber, e);
        }
    }

    @GetMapping("/findByName/{clientName}")
    public AccountDTO getAccountByClientName(@PathVariable(name = "clientName") String clientName) {
        try{
            return accountService.getAccount(clientName);
        }catch (AccountNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found attached to client : " + clientName, e);
        }
    }

    @PostMapping
    public AccountDTO saveAccount(@RequestBody AccountDTO accountDTO){
        return accountService.saveAccount(accountDTO);
    }

    @PutMapping("/deposit")
    public AccountDTO deposit(@RequestBody RestOperationDTO operation) {
        try{
            return accountService.deposit(operation.getAccountNumber(), operation.getAmount(), operation.getDescription());
        }catch (AccountNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with account number : " + operation.getAccountNumber(), e);
        }
    }

    @PutMapping("/withdrawal")
    public AccountDTO withdrawal(@RequestBody RestOperationDTO operation) {
        try{
            return accountService.withdrawal(operation.getAccountNumber(), operation.getAmount(), operation.getDescription());
        }catch (AccountNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with account number : " + operation.getAccountNumber(), e);
        }catch (BalanceNotSufficientException be){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not sufficient to do this operation.", be);
        }
    }
}
