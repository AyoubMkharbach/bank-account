package com.ayoub.bankaccount.cucumber.withdrawal;

import com.ayoub.bankaccount.cucumber.HttpClient;
import com.ayoub.bankaccount.dtos.AccountDTO;
import com.ayoub.bankaccount.entites.Account;
import com.ayoub.bankaccount.repositories.AccountRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WithdrawalFromAccountSteps {

    @Autowired
    private HttpClient httpClient;
    @Autowired
    AccountRepository accountRepository;

    private AccountDTO accountDTO;
    private double oldBalance;
    private double newBalance;

    @Before
    public void beforeScenario() {
        Account account = new Account();
        account.setAccountNumber(1L);
        account.setClientName("Maria");
        account.setBalance(2000);
        accountRepository.save(account);
    }

    @Given("A client named {string} with {double} EUR in her account")
    public void client_named_with_EUR_in_her_account(String clientName, double balance) {
        ResponseEntity<AccountDTO> response = httpClient.getAccountByName(clientName);
        accountDTO = response.getBody();
        oldBalance = balance;
        assertThat(accountDTO.getBalance()).isEqualTo(balance);
        assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }

    @When("(s)he withdrawal {double} EUR from her/his account")
    public void she_withdrawal_EUR_from_her_account(double amount) {
        ResponseEntity<AccountDTO> response = httpClient.withdrawalFromAccount(accountDTO.getAccountNumber(), amount, "Withdrawal with cucumber");
        newBalance = response.getBody().getBalance();
        assertThat(oldBalance).isEqualTo(newBalance + amount);
        assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }

    @Then("the new balance is {double} EUR")
    public void the_new_balance_is_EUR(double expectedBalance) {
        assertThat(newBalance).isEqualTo(expectedBalance);
    }
}
