package com.ayoub.bankaccount.cucumber;

import com.ayoub.bankaccount.dtos.AccountDTO;
import com.ayoub.bankaccount.dtos.RestOperationDTO;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class HttpClient {

    private final String SERVER_URL = "http://localhost";
    private final String ACCOUNTS_ENDPOINT = "/accounts";
    private final String DEPOSIT_ACCOUNTS_ENDPOINT = "/deposit";
    private final String WITHDRAWAL_ACCOUNTS_ENDPOINT = "/withdrawal";

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();

    private String accountsEndpoint() {
        return SERVER_URL + ":" + port + ACCOUNTS_ENDPOINT;
    }

    private String depositAccountsEndpoint() {
        return accountsEndpoint() + DEPOSIT_ACCOUNTS_ENDPOINT;
    }

    private String withdrawalAccountsEndpoint() {
        return accountsEndpoint() + WITHDRAWAL_ACCOUNTS_ENDPOINT;
    }

    public ResponseEntity<AccountDTO> createAccount(AccountDTO accountDTO){
        return restTemplate.postForEntity(accountsEndpoint(), accountDTO, AccountDTO.class);
    }

    public ResponseEntity<AccountDTO> getAccount(long accountNumber){
        return restTemplate.getForEntity(accountsEndpoint() + "/{accountNumber}", AccountDTO.class, accountNumber);
    }

    public ResponseEntity<AccountDTO> getAccountByName(String clientName){
        return restTemplate.getForEntity(accountsEndpoint() + "/findByName/{clientName}", AccountDTO.class, clientName);
    }

    public ResponseEntity<AccountDTO> depositIntoAccount(long accountNumber, double amount, String description){
        RestOperationDTO operation = new RestOperationDTO(accountNumber, amount, description);
        HttpEntity<RestOperationDTO> entity = new HttpEntity<>(operation);
        return restTemplate.exchange(depositAccountsEndpoint(), HttpMethod.PUT, entity, AccountDTO.class);
    }

    public ResponseEntity<AccountDTO> withdrawalFromAccount(long accountNumber, double amount, String description){
        RestOperationDTO operation = new RestOperationDTO(accountNumber, amount, description);
        HttpEntity<RestOperationDTO> entity = new HttpEntity<>(operation);
        return restTemplate.exchange(withdrawalAccountsEndpoint(), HttpMethod.PUT, entity, AccountDTO.class);
    }

}
