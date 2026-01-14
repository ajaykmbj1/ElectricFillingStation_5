Feature: Invoice
  As a System
  I want to generate and view invoices

  Scenario: Generate an Invoice for a Session
    Given the Filling Station Network is available
    And an invoice location exists with ID "Favoriten"
    # Renamed step below to avoid conflict:
    And I configure the invoice price at "Favoriten" to 0.50 per kWh and 0.10 per min for "AC"
    And a valid session "SESS-100" exists at "Favoriten" with 10.0 kWh and 10.0 minutes duration
    When I generate an invoice for session "SESS-100" at location "Favoriten"
    Then the invoice amount should be 6.00

  # Update the other scenarios similarly to use "I configure the invoice price..."

  Scenario: Verify Invoice Calculation for High Energy Usage
    Given the Filling Station Network is available
    And I configure the invoice price at "Margareten" to 1.00 per kWh and 0.00 per min for "DC"
    And a valid session "SESS-HIGH-E" exists at "Margareten" with 100.0 kWh and 30.0 minutes duration
    When I generate an invoice for session "SESS-HIGH-E" at location "Margareten"
    Then the invoice amount should be 100.00

  Scenario: Verify Invoice Calculation for Time Based Usage
    Given the Filling Station Network is available
    And I configure the invoice price at "Simmering" to 0.00 per kWh and 1.00 per min for "AC"
    And a valid session "SESS-TIME" exists at "Simmering" with 5.0 kWh and 60.0 minutes duration
    When I generate an invoice for session "SESS-TIME" at location "Simmering"
    Then the invoice amount should be 60.00