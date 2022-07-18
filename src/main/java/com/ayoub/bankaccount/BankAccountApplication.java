package com.ayoub.bankaccount;

import com.ayoub.bankaccount.dtos.AccountDTO;
import com.ayoub.bankaccount.exceptions.AccountNotFoundException;
import com.ayoub.bankaccount.exceptions.BalanceNotSufficientException;
import com.ayoub.bankaccount.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class BankAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AccountService accountService){
        return args -> {
            AccountDTO account = new AccountDTO();
            account.setBalance(0);
            account.setClientName("Maria");

            account = accountService.saveAccount(account);

            for(int i = 0; i < 3; i++){
                try {
                    accountService.deposit(account.getAccountNumber(), 100 + Math.random() * 12000, "Deposit " + (i+1));
                    accountService.withdrawal(account.getAccountNumber(), 100 + Math.random() * 1200, "Withdraw " + (i+1));
                } catch (AccountNotFoundException | BalanceNotSufficientException e) {
                    e.printStackTrace();
                }
            }

        };
    }

}
