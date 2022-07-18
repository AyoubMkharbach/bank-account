package com.ayoub.bankaccount.cucumber.deposit;

import com.ayoub.bankaccount.cucumber.HttpClient;
import com.ayoub.bankaccount.dtos.AccountDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepositIntoAccountSteps {

    @Autowired
    private HttpClient httpClient;

    private AccountDTO accountDTO;
    private double newBalance;

    @Given("A client named {string} with {double} EUR in her account")
    public void client_named_with_EUR_in_her_account(String clientName, double balance) {
        AccountDTO accountToSave = new AccountDTO();
        accountToSave.setClientName(clientName);
        accountToSave.setBalance(balance);

        ResponseEntity<AccountDTO> response = httpClient.createAccount(accountToSave);
        accountDTO = response.getBody();
        int statusCode = response.getStatusCodeValue();
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @When("(s)he deposits {double} EUR into her/his account")
    public void she_deposits_EUR_into_her_account(double amount) {
        ResponseEntity<AccountDTO> response = httpClient.depositIntoAccount(accountDTO.getAccountNumber(), amount, "Deposit with cucumber");
        newBalance = response.getBody().getBalance();
        int statusCode = response.getStatusCodeValue();
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @Then("the new balance is {double} EUR")
    public void the_balance_is_EUR(double expectedBalance) {
        assertThat(newBalance).isEqualTo(expectedBalance);
    }

}
