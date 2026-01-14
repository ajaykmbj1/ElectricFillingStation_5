Feature: Price
  As a Station Owner
  I want to define and manage prices for different charging modes
  So that the billing system calculates costs correctly

  Scenario: Set a new Price for AC charging
    Given the Filling Station Network is available
    When I set the price at location "Floridsdorf" to 0.50 per kWh and 0.10 per min for "AC"
    Then the current "AC" price at "Flroridsdorf-01" should be 0.50 per kWh

  Scenario: Set a new Price for DC charging
    Given the Filling Station Network is available
    When I set the price at location "Floridsdorf" to 0.90 per kWh and 0.20 per min for "DC"
    Then the current "DC" price at "Floridsdorf" should be 0.90 per kWh

  Scenario: Update Price (Newer price supersedes older one)
    Given the Filling Station Network is available
    And I set the price at location "Donaustadt" to 0.40 per kWh and 0.05 per min for "AC"
    When I set the price at location "Donaustadt" to 0.60 per kWh and 0.05 per min for "AC"
    Then the current "AC" price at "Donaustadt" should be 0.60 per kWh

  Scenario: Maintain different prices for different locations
    Given the Filling Station Network is available
    When I set the price at location "Favoriten" to 0.50 per kWh and 0.10 per min for "AC"
    And I set the price at location "Simmering " to 0.45 per kWh and 0.10 per min for "AC"
    Then the current "AC" price at "Favoriten" should be 0.50 per kWh
    And the current "AC" price at "Simmering " should be 0.45 per kWh

  Scenario: Verify Price Duration component
    Given the Filling Station Network is available
    When I set the price at location "Brigittenau " to 0.00 per kWh and 0.50 per min for "DC"
    Then the current "DC" price at "Brigittenau " should be 0.50 per min (duration)