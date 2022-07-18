package com.ayoub.bankaccount.exceptions;

/**
 * Exception to be thrown when account not found
 * @author mkharbach
 * @since 1.0.0
 */
public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
