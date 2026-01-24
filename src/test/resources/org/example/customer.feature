Feature: Customer
  As an Owner
  I want to manage customers (Register, Update, Top Up, Delete)
  So that I can maintain user accounts

  Scenario: Register a new Customer
    Given the Customer Manager is ready
    When I register a customer with ID "CUST-01", name "Alice", and balance 50.00
    Then the customer "CUST-01" should exist
    And the customer "CUST-01" should have a balance of 50.00

  Scenario: Read Customer Details
    Given the Customer Manager is ready
    And I register a customer with ID "CUST-READ", name "Bob", and balance 20.00
    Then I can read the details of customer "CUST-READ"
    And the customer name should be "Bob"

  Scenario: Update Customer Name
    Given the Customer Manager is ready
    And I register a customer with ID "CUST-UPD", name "Charlie", and balance 10.00
    When I update the name of customer "CUST-UPD" to "Charles"
    Then the customer name for "CUST-UPD" should be "Charles"

  Scenario: Top Up Customer Balance
    Given the Customer Manager is ready
    And I register a customer with ID "CUST-PAY", name "David", and balance 5.00
    When I top up the balance of "CUST-PAY" by 15.00
    Then the customer "CUST-PAY" should have a balance of 20.00

  Scenario: Delete a Customer
    Given the Customer Manager is ready
    And I register a customer with ID "CUST-DEL", name "Eve", and balance 0.00
    When I delete the customer "CUST-DEL"
    Then the customer "CUST-DEL" should no longer exist