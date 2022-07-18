Feature: Deposit into account
  As a client
  I want to check my operations
  In order to see my account operations history

  Scenario: Check my operations
    Given A client with account number 1 did the following operations
      | amount | type       | description            |
      | 150.0  | DEPOSIT    | Prime                  |
      | 300.0  | DEPOSIT    | Salary                 |
      | 400.0  | WITHDRAWAL | Credit                 |
      | 200.0  | DEPOSIT    | Travel savings         |
      | 100.0  | WITHDRAWAL | Internet subscription  |
    When she request to check the list of her operations
    Then she can see 3 deposits and 2 withdrawals