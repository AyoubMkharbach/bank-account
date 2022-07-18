package com.ayoub.bankaccount.services;

import com.ayoub.bankaccount.dtos.AccountDTO;
import com.ayoub.bankaccount.exceptions.AccountNotFoundException;
import com.ayoub.bankaccount.exceptions.BalanceNotSufficientException;

/**
 * Account business service
 * @author mkharbach
 * @since 1.0.0
 */
public interface AccountService {

    /**
     * Save account into DB
     * @param accountDTO account to save
     * @return saved account
     */
    AccountDTO saveAccount(AccountDTO accountDTO);

    /**
     * Deposit new amount in account and log that as operation
     * @param accountNumber account number
     * @param amount amount
     * @param description operation description
     * @return Updated account
     * @throws AccountNotFoundException exception to thrown when new account not found
     */
    AccountDTO deposit(long accountNumber, double amount, String description) throws AccountNotFoundException;

    /**
     * Get account by account number
     * @param accountNumber account number
     * @return account 
     * @throws AccountNotFoundException exception to throw when new account not found
     */
    AccountDTO getAccount(long accountNumber) throws AccountNotFoundException;

    /**
     * Get account by client name
     * @param clientName client name
     * @return account
     * @throws AccountNotFoundException exception to throw when new account not found
     */
    AccountDTO getAccount(String clientName) throws AccountNotFoundException;

    /**
     * Withdrawal new amount in account and log that as operation
     * @param accountNumber account number
     * @param amount amount
     * @param description operation description
     * @return Updated account
     * @throws AccountNotFoundException exception to throw when new account not found
     * @throws BalanceNotSufficientException exception to throw when balance is not sufficient
     */
    AccountDTO withdrawal(long accountNumber, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException;
}
