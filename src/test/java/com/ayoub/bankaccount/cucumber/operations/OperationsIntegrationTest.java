package com.ayoub.bankaccount.cucumber.operations;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/operations",
        plugin = {"pretty", "html:target/cucumber"},
        glue = "com.ayoub.bankaccount.cucumber.operations")
public class OperationsIntegrationTest {
}
