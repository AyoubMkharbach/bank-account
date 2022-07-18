Feature: Deposit into account
  As a client
  I want to put money into my account
  In order to have a positive balance

  Scenario: Deposit money into account
    Given A client named "Maria" with 0.0 EUR in her account
    When she deposits 10.0 EUR into her account
    Then the new balance is 10.0 EUR