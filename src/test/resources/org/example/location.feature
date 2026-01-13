Feature: Location
  As an Owner
  I want to create and view locations
  So that I can manage my locations

  Scenario: Create a new Location
    Given the Filling Station Network is available
    When I create a location with ID "LOC-01" and name "Floridsdorf"
    Then the system should return a location details string containing "Floridsdorf"

  Scenario: Read an existing Location
    Given the Filling Station Network is available
    And a location exists with ID "LOC-READ-01" and name "Favoriten"
    Then the location "LOC-READ-01" should be retrievable

  Scenario: Update a Location Name
    Given the Filling Station Network is available
    And a location exists with ID "LOC-UPD-01" and name "Floridsdorf"
    When I update the location "LOC-UPD-01" to have the name "Donaustadt"
    Then the system should return a location details string containing "Donaustadt"
    And the location "LOC-UPD-01" should have the name "Donaustadt"

  Scenario: Delete a Location
    Given the Filling Station Network is available
    And a location exists with ID "LOC-DEL-01" and name "Temporary Site"
    When I delete the location with ID "LOC-DEL-01"
    Then the location "LOC-DEL-01" should no longer be retrievable

  Scenario: Attempt to Create Duplicate Location
    Given the Filling Station Network is available
    And a location exists with ID "LOC-DUP-01" and name "First Site"
    When I attempt to create a location with ID "LOC-DUP-01" and name "Duplicate Site"
    Then the system should not create the second location
    And the location "LOC-DUP-01" should still have the name "First Site"