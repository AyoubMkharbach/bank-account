package com.ayoub.bankaccount.cucumber.withdrawal;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/withdrawal",
        plugin = {"pretty", "html:target/cucumber"},
        glue = "com.ayoub.bankaccount.cucumber.withdrawal")
public class WithdrawalIntegrationTest {
}
