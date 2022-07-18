Feature: Withdrawal from account
  As a client
  I want to withdrawal money from my account
  In order to retrieve some or all of my savings

  Scenario: Withdrawal some money from my account
    Given A client named "Maria" with 2000.0 EUR in her account
    When she withdrawal 150.0 EUR from her account
    Then the new balance is 1850.0 EUR