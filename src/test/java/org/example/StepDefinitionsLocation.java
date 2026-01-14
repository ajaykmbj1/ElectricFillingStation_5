package org.example;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsLocation {

    // Assuming you have these classes.
    // If not, see the "Missing Classes" section below!
    private LocationManager locationManager = new LocationManager();
    private Location lastReadLocation;
    private Exception lastError;

    @Given("the Location Manager is ready")
    public void the_location_manager_is_ready() {
        locationManager = new LocationManager();
        lastError = null;
    }

    @When("I create a location with ID {string} and name {string}")
    public void i_create_a_location(String id, String name) {
        try {
            locationManager.createLocation(id, name);
        } catch (Exception e) {
            lastError = e;
        }
    }

    @When("I try to create another location with ID {string} and name {string}")
    public void i_try_to_create_duplicate_location(String id, String name) {
        i_create_a_location(id, name);
    }

    @Then("the location {string} should exist")
    public void the_location_should_exist(String id) {
        Location loc = locationManager.readLocation(id);
        assertNotNull(loc, "Location " + id + " should exist.");
    }

    @Then("the location {string} should have the name {string}")
    public void the_location_should_have_name(String id, String expectedName) {
        Location loc = locationManager.readLocation(id);
        assertNotNull(loc);
        assertEquals(expectedName, loc.getName());
    }

    @Then("I can read the location {string}")
    public void i_can_read_the_location(String id) {
        lastReadLocation = locationManager.readLocation(id);
        assertNotNull(lastReadLocation);
        assertEquals(id, lastReadLocation.getId());
    }

    @Then("the location name should be {string}")
    public void last_read_location_name_should_be(String expectedName) {
        assertNotNull(lastReadLocation);
        assertEquals(expectedName, lastReadLocation.getName());
    }

    @When("I update the location {string} to name {string}")
    public void i_update_location_name(String id, String newName) {
        Location loc = locationManager.readLocation(id);
        assertNotNull(loc, "Cannot update missing location");

        // Assuming you have a setter or update method
        loc.updateName(newName);
        locationManager.updateLocation(loc);
    }

    @When("I delete the location {string}")
    public void i_delete_location(String id) {
        locationManager.deleteLocation(id);
    }

    @Then("the location {string} should no longer exist")
    public void location_should_no_longer_exist(String id) {
        Location loc = locationManager.readLocation(id);
        assertNull(loc, "Location " + id + " should have been deleted.");
    }

    @Then("I should receive a location error message {string}")
    public void i_should_receive_error_message(String expectedMsgFragment) {
        assertNotNull(lastError, "Expected an error but none occurred!");
        String actualMsg = lastError.getMessage();
        assertTrue(actualMsg.contains(expectedMsgFragment),
                "Expected error '" + expectedMsgFragment + "' but got: " + actualMsg);
    }
}