Feature: Price
  As an Owner
  I want to manage charging prices
  So that the system calculates the correct costs for customers

  Scenario: Set and Read a Price for a Location
    Given the Price Manager is ready
    When I set the price at location "LOC-01" to 0.50 per kWh and 0.05 per min for "AC"
    Then the current "AC" price at "LOC-01" should be 0.50 per kWh and 0.05 per min

  Scenario: Update Price (Newer Valid Price overrides older)
    Given the Price Manager is ready
    And I set the price at location "LOC-02" to 0.40 per kWh and 0.04 per min for "DC"
    When I update the price at location "LOC-02" to 0.60 per kWh and 0.06 per min for "DC"
    Then the current "DC" price at "LOC-02" should be 0.60 per kWh and 0.06 per min

  Scenario: Distinct Prices for Different Charger Modes
    Given the Price Manager is ready
    And I set the price at location "LOC-03" to 0.30 per kWh for "AC"
    And I set the price at location "LOC-03" to 0.90 per kWh for "DC"
    Then the current "AC" price at "LOC-03" should be 0.30 per kWh
    And the current "DC" price at "LOC-03" should be 0.90 per kWh

  Scenario: Verify Price List Contains History
    Given the Price Manager is ready
    And I set the price at location "LOC-HIST" to 0.50 per kWh for "AC"
    And I update the price at location "LOC-HIST" to 0.55 per kWh for "AC"
    Then location "LOC-HIST" should have 2 prices stored

  Scenario: Check Price Validity Timestamp
    Given the Price Manager is ready
    When I set the price at location "LOC-TIME" to 0.55 per kWh for "AC"
    Then the current "AC" price at "LOC-TIME" should be valid from now

  Scenario: Edge Case - Set Free Charging Price
    Given the Price Manager is ready
    When I set the price at location "LOC-FREE" to 0.00 per kWh and 0.00 per min for "AC"
    Then the current "AC" price at "LOC-FREE" should be 0.00 per kWh and 0.00 per min