package com.ayoub.bankaccount.exceptions;

/**
 * Exception to be thrown when balance not sufficient
 * @author mkharbach
 * @since 1.0.0
 */
public class BalanceNotSufficientException extends Exception {

    public BalanceNotSufficientException(String errorMessage) {
        super(errorMessage);
    }
}
