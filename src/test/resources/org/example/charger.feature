Feature: Charger
  As an Owner
  I want to manage chargers (Create, Read, Update, Delete)
  So that the infrastructure matches the physical stations

  Scenario: Create a new Charger
    Given the Charger Manager is ready
    When I create a charger with ID "CH-01" of type "DC"
    Then the charger "CH-01" should exist
    And the charger "CH-01" should have type "DC"

  Scenario: Read an existing Charger
    Given the Charger Manager is ready
    And I create a charger with ID "CH-READ" of type "AC"
    Then I can read the charger "CH-READ"
    And the charger "CH-READ" should have status "FREE"

  Scenario: Update Charger Type
    Given the Charger Manager is ready
    And I create a charger with ID "CH-UPDATE" of type "AC"
    When I update the charger "CH-UPDATE" to type "DC"
    Then the charger "CH-UPDATE" should have type "DC"

  Scenario: Delete a Charger
    Given the Charger Manager is ready
    And I create a charger with ID "CH-DEL" of type "AC"
    When I delete the charger "CH-DEL"
    Then the charger "CH-DEL" should no longer exist

  Scenario: Attempt to create duplicate Charger
    Given the Charger Manager is ready
    And I create a charger with ID "CH-DUP" of type "AC"
    When I try to create another charger with ID "CH-DUP" of type "DC"
    Then I should receive an error message "Charger ID already exists"

  Scenario: Edge Case - Overwrite duplicate Charger ID
    Given the Filling Station Network is available
    And I create a charger with ID "CH-EDGE" of type "AC"
    When I create a charger with ID "CH-EDGE" of type "DC"
    Then the charger "CH-EDGE" should exist
    And the charger "CH-EDGE" should have type "DC"