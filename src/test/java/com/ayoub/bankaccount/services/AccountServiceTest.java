package com.ayoub.bankaccount.services;

import com.ayoub.bankaccount.dtos.AccountDTO;
import com.ayoub.bankaccount.dtos.AccountOperationDTO;
import com.ayoub.bankaccount.entites.Account;
import com.ayoub.bankaccount.entites.AccountOperation;
import com.ayoub.bankaccount.enums.OperationType;
import com.ayoub.bankaccount.exceptions.AccountNotFoundException;
import com.ayoub.bankaccount.exceptions.BalanceNotSufficientException;
import com.ayoub.bankaccount.repositories.AccountOperationRepository;
import com.ayoub.bankaccount.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock private AccountRepository accountRepository;
    @Mock private AccountOperationRepository operationRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private static final String DESCRIPTION = "Description";

    @Test
    public void givenAccountObject_whenSaveAccount_thenReturnAccountObject(){
        Account account = new Account();
        account.setAccountNumber(1L);
        account.setClientName("Maria");
        account.setBalance(2000);

        AccountDTO accountToSave = new AccountDTO();
        accountToSave.setClientName("Maria");
        accountToSave.setBalance(2000);

        AccountDTO mappedAccount = new AccountDTO();
        mappedAccount.setAccountNumber(1L);
        mappedAccount.setClientName("Maria");
        mappedAccount.setBalance(2000);

        given(accountRepository.save(any(Account.class))).willReturn(account);

        AccountDTO accountDTO = accountService.saveAccount(accountToSave);

        assertThat(accountDTO.getAccountNumber()).isEqualTo(1L);

    }

    @Test()
    public void givenNegativeAmount_whenDeposit_thenReturnIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.deposit(1L, -5, DESCRIPTION));
    }

    @Test()
    public void givenNoExistsAccount_whenDeposit_thenReturnAccountNotFoundException(){
        given(accountRepository.findById(1L)).willReturn(Optional.ofNullable(null));
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.deposit(1L, 5, DESCRIPTION));
    }

    @Test()
    public void givenExistingAccount_whenDeposit_thenReturnAccountWithNewBalanceAndOperation() throws AccountNotFoundException {
        AccountOperation operation = new AccountOperation();
        operation.setOperationType(OperationType.DEPOSIT);
        operation.setAmount(500);

        Account account = new Account();
        account.setAccountNumber(1L);
        account.setClientName("Maria");
        account.setBalance(2000);

        given(accountRepository.findById(1L)).willReturn(Optional.of(account));
        given(operationRepository.save(any(AccountOperation.class))).willReturn(operation);
        given(accountRepository.save(any(Account.class))).willReturn(account);

        AccountDTO accountDTO = accountService.deposit(1L, 500, DESCRIPTION);

        assertThat(accountDTO.getBalance()).isEqualTo(2500);
        assertThat(accountDTO.getOperations().size()).isEqualTo(1);
        AccountOperationDTO newOperation = accountDTO.getOperations().get(0);
        assertThat(newOperation.getAmount()).isEqualTo(500);
        assertThat(newOperation.getOperationType()).isEqualTo(OperationType.DEPOSIT);

    }

    @Test()
    public void givenExistingAccount_whenSearchByClientName_thenReturnAccount() throws AccountNotFoundException {

        Account account = new Account();
        account.setAccountNumber(1L);
        account.setClientName("Maria");
        account.setBalance(2000);

        given(accountRepository.findAccountByClientName("Maria")).willReturn(Optional.of(account));

        AccountDTO accountDTO = accountService.getAccount("Maria");

        assertThat(accountDTO.getAccountNumber()).isEqualTo(1L);
        assertThat(accountDTO.getBalance()).isEqualTo(2000);
    }

    @Test()
    public void givenNoExistsAccount_whenSearchByClientName_thenReturnAccountNotFoundException(){
        given(accountRepository.findAccountByClientName("Maria")).willReturn(Optional.ofNullable(null));
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getAccount("Maria"));
    }

    @Test()
    public void givenNegativeAmount_whenWithdrawal_thenReturnIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.withdrawal(1L, -5, DESCRIPTION));
    }

    @Test()
    public void givenNoExistsAccount_whenWithdrawal_thenReturnAccountNotFoundException(){
        given(accountRepository.findById(1L)).willReturn(Optional.ofNullable(null));
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.withdrawal(1L, 5, DESCRIPTION));
    }

    @Test()
    public void givenBalanceNotSufficient_whenWithdrawal_thenReturnAccountBalanceNotSufficientException(){
        Account account = new Account();
        account.setAccountNumber(1L);
        account.setClientName("Maria");
        account.setBalance(2000);

        given(accountRepository.findById(1L)).willReturn(Optional.of(account));
        Assertions.assertThrows(BalanceNotSufficientException.class, () -> accountService.withdrawal(1L, 3000, DESCRIPTION));
    }

    @Test()
    public void givenExistingAccount_whenWithdrawal_thenReturnAccountWithNewBalanceAndOperation() throws AccountNotFoundException, BalanceNotSufficientException {
        AccountOperation operation = new AccountOperation();
        operation.setOperationType(OperationType.WITHDRAWAL);
        operation.setAmount(500);

        Account account = new Account();
        account.setAccountNumber(1L);
        account.setClientName("Maria");
        account.setBalance(2000);

        given(accountRepository.findById(1L)).willReturn(Optional.of(account));
        given(operationRepository.save(any(AccountOperation.class))).willReturn(operation);
        given(accountRepository.save(any(Account.class))).willReturn(account);

        AccountDTO accountDTO = accountService.withdrawal(1L, 500, DESCRIPTION);

        assertThat(accountDTO.getBalance()).isEqualTo(1500);
        assertThat(accountDTO.getOperations().size()).isEqualTo(1);
        AccountOperationDTO newOperation = accountDTO.getOperations().get(0);
        assertThat(newOperation.getAmount()).isEqualTo(500);
        assertThat(newOperation.getOperationType()).isEqualTo(OperationType.WITHDRAWAL);

    }
}
