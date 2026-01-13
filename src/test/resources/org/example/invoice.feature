Feature: Invoice
  As a System
  I want to generate and view invoices
  So that I can bill customers based on their usage

  Scenario: Generate an Invoice for a Session
    Given the Filling Station Network is available
    And a location exists with ID "Favoriten" and name "Billing Point"
    And I set the price at location "Favoriten" to 0.50 per kWh and 0.10 per min for "AC"
    And a valid session "SESS-100" exists at "Favoriten" with 10.0 kWh and 10.0 minutes duration
    When I generate an invoice for session "Favoriten" at location "Favoriten"
    Then the invoice amount should be 6.00

  Scenario: Retrieve an Invoice by ID
    Given the Filling Station Network is available
    And an invoice exists with ID "INV-READ-01" and amount 15.50
    Then the invoice "INV-READ-01" should be retrievable
    And the invoice total should be 15.50

  Scenario: Read all Invoices for the system
    Given the Filling Station Network is available
    And an invoice exists with ID "INV-LIST-01" and amount 10.00
    And an invoice exists with ID "INV-LIST-02" and amount 20.00
    Then the system should contain at least 2 invoices

  Scenario: Verify Invoice Calculation for High Energy Usage
    Given the Filling Station Network is available
    And I set the price at location "Margareten" to 1.00 per kWh and 0.00 per min for "DC"
    And a valid session "SESS-HIGH-E" exists at "Margareten" with 100.0 kWh and 30.0 minutes duration
    When I generate an invoice for session "SESS-HIGH-E" at location "Margareten"
    Then the invoice amount should be 100.00

  Scenario: Verify Invoice Calculation for Time Based Usage
    Given the Filling Station Network is available
    And I set the price at location "Simmering" to 0.00 per kWh and 1.00 per min for "AC"
    And a valid session "SESS-TIME" exists at "Simmering" with 5.0 kWh and 60.0 minutes duration
    When I generate an invoice for session "SESS-TIME" at location "Simmering"
    Then the invoice amount should be 60.00