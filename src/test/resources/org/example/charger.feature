Feature: Charger
  As an Owner
  I want to create, view, update, and delete chargers
  So that I can manage my infrastructure and ensure compatibility (AC/DC)

  Scenario: Create (Add) a Charger to a Location
    Given the Filling Station Network is available
    And a location exists with ID "LOC-02" and name "Donaustadt"
    When I add a charger with ID "CHG-100" of type "DC" to location "LOC-02"
    Then the location "LOC-02" should contain a charger with ID "CHG-100"

  Scenario: Read Charger details
    Given the Filling Station Network is available
    And a location exists with ID "LOC-03" and name "Ottakring"
    And I add a charger with ID "CHG-READ-200" of type "AC" to location "LOC-03"
    Then the charger details should include "AC"

  Scenario: Update Charger Type
    Given the Filling Station Network is available
    And a location exists with ID "LOC-UPD-01" and name "Favoriten"
    And I add a charger with ID "CHG-UPD-50" of type "AC" to location "LOC-UPD-01"
    When I update the charger "CHG-UPD-50" to have type "DC"
    Then the charger details should include "DC"

  Scenario: Delete a Charger
    Given the Filling Station Network is available
    And a location exists with ID "LOC-DEL-01" and name "Simmering"
    And I add a charger with ID "CHG-DEL-99" of type "DC" to location "LOC-DEL-01"
    When I delete the charger "CHG-DEL-99" from location "LOC-DEL-01"
    Then the location "LOC-DEL-01" should not contain charger "CHG-DEL-99"

  Scenario: Attempt to Create Duplicate Charger
    Given the Filling Station Network is available
    And a location exists with ID "LOC-DUP-01" and name "Leopoldstadt"
    And I add a charger with ID "CHG-DUP-01" of type "AC" to location "LOC-DUP-01"
    When I attempt to add a charger with ID "CHG-DUP-01" of type "DC" to location "LOC-DUP-01"
    Then the system should not create the second charger
    And the charger details should include "AC"