Feature: Charging Session Management
  As a System
  I want to track charging sessions accurately
  So that I know exactly when a car started and stopped charging

  Scenario: Start a Session and Verify Timestamp
    Given the Filling Station Network is available
    And I create a charger with ID "CH-TIME-01" of type "AC"
    And I register a customer with ID "CUST-TIME-01", name "Tim", and balance 50.00
    When I start a session "SESS-T1" at charger "CH-TIME-01" for customer "CUST-TIME-01"
    Then the session "SESS-T1" should be active
    And the session "SESS-T1" should have a valid start time

  Scenario: Stop a Session and Verify Duration
    Given the Filling Station Network is available
    And I create a charger with ID "CH-TIME-02" of type "DC"
    And I register a customer with ID "CUST-TIME-02", name "Tom", and balance 50.00
    And I start a session "SESS-T2" at charger "CH-TIME-02" for customer "CUST-TIME-02"
    When I wait for 1 seconds
    And I stop the session "SESS-T2"
    Then the session "SESS-T2" should have a valid end time
    And the session end time should be after the start time