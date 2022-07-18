package com.ayoub.bankaccount.cucumber.operations;

import com.ayoub.bankaccount.cucumber.HttpClient;
import com.ayoub.bankaccount.dtos.AccountDTO;
import com.ayoub.bankaccount.entites.Account;
import com.ayoub.bankaccount.enums.OperationType;
import com.ayoub.bankaccount.repositories.AccountRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CheckAccountOperationsSteps {

    @Autowired
    private HttpClient httpClient;
    @Autowired
    AccountRepository accountRepository;

    private long accountNumber;
    private AccountDTO accountDTO;

    @Before
    public void beforeScenario() {
        Account account = new Account();
        account.setClientName("Maria");
        account.setBalance(2000);
        accountRepository.save(account);
    }

    @Given("A client with account number {long} did the following operations")
    public void client_named_with_did_operations(long accountNumber, DataTable dataTable) {
        List<Map<String, String>> operations = dataTable.asMaps();

        for (Map<String, String> operation : operations) {
            double amount = Double.valueOf(operation.get("amount"));

            int status;
            if(OperationType.DEPOSIT.name().equals(operation.get("type"))){
                status = httpClient.depositIntoAccount(accountNumber, amount, operation.get("description")).getStatusCodeValue();
            }else{
                status = httpClient.withdrawalFromAccount(accountNumber, amount, operation.get("description")).getStatusCodeValue();
            }
            assertThat(status).isEqualTo(HttpStatus.OK.value());
        }

        this.accountNumber =  accountNumber;
    }

    @When("(s)he request to check the list of her/his operations")
    public void she_request_to_check_operations() {
        ResponseEntity<AccountDTO> response = httpClient.getAccount(accountNumber);
        accountDTO = response.getBody();
        assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }

    //@Then("(s)he can see {double} deposit(s) and {double} withdrawal(s)")
    @Then("(s)he can see {int} deposit(s) and {int} withdrawal(s)")
    public void she_can_see_deposits_and_withdrawals(int expectedDepositNumber, int expectedWithdrawalNumber) {
        int depositNumber = (int) accountDTO.getOperations().stream()
                .filter(operation -> OperationType.DEPOSIT.equals(operation.getOperationType())).count();
        int withdrawalNumber = (int) accountDTO.getOperations().stream()
                .filter(operation -> OperationType.WITHDRAWAL.equals(operation.getOperationType())).count();

        assertThat(expectedDepositNumber).isEqualTo(depositNumber);
        assertThat(expectedWithdrawalNumber).isEqualTo(withdrawalNumber);
        assertThat(accountDTO.getOperations().size()).isEqualTo(expectedDepositNumber + expectedWithdrawalNumber);
    }
}
