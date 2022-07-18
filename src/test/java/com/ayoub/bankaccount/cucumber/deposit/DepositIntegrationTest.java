package com.ayoub.bankaccount.cucumber.deposit;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/deposit",
        plugin = {"pretty", "html:target/cucumber"},
        glue = "com.ayoub.bankaccount.cucumber.deposit")
public class DepositIntegrationTest {
}
