package com.ayoub.bankaccount.services;

import com.ayoub.bankaccount.dtos.AccountDTO;
import com.ayoub.bankaccount.entites.Account;
import com.ayoub.bankaccount.entites.AccountOperation;
import com.ayoub.bankaccount.enums.OperationType;
import com.ayoub.bankaccount.exceptions.AccountNotFoundException;
import com.ayoub.bankaccount.exceptions.BalanceNotSufficientException;
import com.ayoub.bankaccount.mappers.Mapper;
import com.ayoub.bankaccount.repositories.AccountOperationRepository;
import com.ayoub.bankaccount.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Account business service implementation
 * @author mkharbach
 * @since 1.0.0
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountOperationRepository operationRepository;

    @Override
    public AccountDTO saveAccount(AccountDTO accountDTO) {
        log.info("Start saving new account...");
        Account account = Mapper.fromAccountDTO(accountDTO);
        Account savedAccount = accountRepository.save(account);
        log.info("End saving new account...");
        return Mapper.fromAccount(savedAccount);
    }

    @Override
    public AccountDTO deposit(long accountNumber, double amount, String description) throws AccountNotFoundException {
        log.info("Start deposit in account...");
        if(amount <= 0){
            throw new IllegalArgumentException("Amount should be positive");
        }

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));


        //Add operation line
        AccountOperation operation = new AccountOperation();
        account.addAccountOperation(operation);
        operation.setOperationType(OperationType.DEPOSIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(LocalDateTime.now());
        operationRepository.save(operation);

        //Update balance & save
        account.setBalance(account.getBalance() + amount);
        account = accountRepository.save(account);

        log.info("Start deposit in new account...");
        return Mapper.fromAccount(account);
    }

    @Override
    public AccountDTO getAccount(long accountNumber) throws AccountNotFoundException {
        log.info("Start find account...");
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        log.info("End find account...");
        return Mapper.fromAccount(account);
    }

    @Override
    public AccountDTO getAccount(String clientName) throws AccountNotFoundException {
        log.info("Start find account by name...");
        Account account = accountRepository.findAccountByClientName(clientName)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        log.info("End find account by name...");
        return Mapper.fromAccount(account);
    }

    @Override
    public AccountDTO withdrawal(long accountNumber, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException {
        log.info("Start withdrawal from account...");
        if(amount <= 0){
            throw new IllegalArgumentException("Amount should be positive");
        }

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(account.getBalance() < amount){
            throw new BalanceNotSufficientException("Balance not sufficient");
        }

        //Add operation line
        AccountOperation operation = new AccountOperation();
        account.addAccountOperation(operation);
        operation.setOperationType(OperationType.WITHDRAWAL);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(LocalDateTime.now());
        operationRepository.save(operation);

        //Update balance & save
        account.setBalance(account.getBalance() - amount);
        account = accountRepository.save(account);

        log.info("Start withdrawal from new account...");
        return Mapper.fromAccount(account);
    }
}
