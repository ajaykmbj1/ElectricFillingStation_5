Feature: Invoice Management
  As an Owner
  I want to generate and view invoices
  So that I can bill customers based on their usage

  Scenario: Generate an Invoice for a Session (AC Mixed Calculation)
    Given an invoice location exists with ID "Favoriten"
    And I configure the invoice price at "Favoriten" to 0.50 per kWh and 0.10 per min for "AC"
    And a valid session "SESS-100" exists at "Favoriten" with 10.0 kWh and 10.0 minutes duration and type "AC"
    When I generate an invoice for session "SESS-100" at location "Favoriten"
    Then the invoice amount should be 6.00

  Scenario: Retrieve an Invoice by ID
    Given an invoice exists with ID "INV-READ-01" and amount 15.50
    Then the invoice "INV-READ-01" should be retrievable
    And the invoice total should be 15.50

  Scenario: Read all Invoices for the system
    Given an invoice exists with ID "INV-LIST-01" and amount 10.00
    And an invoice exists with ID "INV-LIST-02" and amount 20.00
    Then the system should contain at least 2 invoices

  Scenario: Verify Invoice Calculation for High Energy Usage
    Given an invoice location exists with ID "Margareten"
    And I configure the invoice price at "Margareten" to 1.00 per kWh and 0.00 per min for "DC"
    And a valid session "SESS-HIGH-E" exists at "Margareten" with 100.0 kWh and 30.0 minutes duration and type "DC"
    When I generate an invoice for session "SESS-HIGH-E" at location "Margareten"
    Then the invoice amount should be 100.00

  Scenario: Check Invoice Date
    Given an invoice location exists with ID "Wien-Mitte"
    And I configure the invoice price at "Wien-Mitte" to 0.50 per kWh and 0.20 per min for "DC"
    And a valid session "SESS-DATE" exists at "Wien-Mitte" with 10.0 kWh and 5.0 minutes duration and type "DC"
    When I generate an invoice for session "SESS-DATE" at location "Wien-Mitte"
    Then the invoice date should be today