Feature: Location
  As an Owner
  I want to manage filling station locations
  So that I can keep track of where chargers are installed

  Scenario: Create a new Location
    Given the Location Manager is ready
    When I create a location with ID "LOC-01" and name "Vienna Central"
    Then the location "LOC-01" should exist
    And the location "LOC-01" should have the name "Vienna Central"

  Scenario: Read an existing Location
    Given the Location Manager is ready
    And I create a location with ID "LOC-READ" and name "Graz North"
    Then I can read the location "LOC-READ"
    And the location name should be "Graz North"

  Scenario: Update Location Name
    Given the Location Manager is ready
    And I create a location with ID "LOC-UPD" and name "Old Name"
    When I update the location "LOC-UPD" to name "New Name"
    Then the location "LOC-UPD" should have the name "New Name"

  Scenario: Delete a Location
    Given the Location Manager is ready
    And I create a location with ID "LOC-DEL" and name "Temporary Site"
    When I delete the location "LOC-DEL"
    Then the location "LOC-DEL" should no longer exist

  Scenario: Attempt to create duplicate Location
    Given the Location Manager is ready
    And I create a location with ID "LOC-DUP" and name "First Site"
    When I try to create another location with ID "LOC-DUP" and name "Second Site"
    Then I should receive a location error message "Location ID already exists"