Feature: Customer
  As a Customer
  I want to register, view, update, and delete my account
  So that I can manage my usage of the charging network

  Scenario: Create a Customer Account
    Given the Filling Station Network is available
    When I register a client with ID "CL-55" and name "Max Mustermann"
    Then the system should return a client details string containing "Max Mustermann"

  Scenario: Read a Customer Account
    Given the Filling Station Network is available
    And a client exists with ID "CL-READ-99" and name "Anna Muster"
    Then the client "CL-READ-99" should be retrievable

  Scenario: Update Customer Balance
    Given the Filling Station Network is available
    And a client exists with ID "CL-UPD-01" and name "Tom Topup"
    When I update the balance of client "CL-UPD-01" to 50.0
    Then the client "CL-UPD-01" should have a balance of 50.0

  Scenario: Delete a Customer Account
    Given the Filling Station Network is available
    And a client exists with ID "CL-DEL-01" and name "Leaving User"
    When I delete the client with ID "CL-DEL-01"
    Then the client "CL-DEL-01" should no longer be retrievable

  Scenario: Attempt to Register Duplicate Customer
    Given the Filling Station Network is available
    And a client exists with ID "CL-DUP-01" and name "Original User"
    When I attempt to register a client with ID "CL-DUP-01" and name "Imposter"
    Then the system should not create the second client
    And the client "CL-DUP-01" should still be named "Original User"