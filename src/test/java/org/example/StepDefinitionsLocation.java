package org.example;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsLocation {

    private LocationManager getLocManager() { return CommonSteps.locationManager; }

    // We don't need ChargerManager here anymore if we remove the addCharger step!
    // private ChargerManager getChgManager() { return CommonSteps.chargerManager; }

    private Location lastLocation;
    private Exception lastException;

    // --- CREATE ---
    @When("I create a location with ID {string} and name {string}")
    public void createLocation(String id, String name) {
        try {
            this.lastLocation = getLocManager().createLocation(id, name);
        } catch (IllegalArgumentException e) {
            this.lastException = e;
        }
    }

    // --- READ ---
    @Then("the system should return a location details string containing {string}")
    public void verifyLocationToString(String substring) {
        assertNotNull(lastLocation, "Location was not created or updated");
        assertTrue(lastLocation.toString().contains(substring));
    }

    @Then("the location {string} should be retrievable")
    public void verifyLocationRetrieval(String id) {
        assertNotNull(getLocManager().readLocation(id));
    }

    // --- UPDATE ---
    @When("I update the location {string} to have the name {string}")
    public void updateLocationName(String id, String newName) {
        Location loc = getLocManager().readLocation(id);
        assertNotNull(loc);
        loc.updateName(newName);
        getLocManager().updateLocation(loc);
        this.lastLocation = loc; // Update reference for verification
    }

    @Then("the location {string} should have the name {string}")
    public void verifyLocationName(String id, String expectedName) {
        assertEquals(expectedName, getLocManager().readLocation(id).getName());
    }

    // --- DELETE ---
    @When("I delete the location with ID {string}")
    public void deleteLocation(String id) {
        getLocManager().deleteLocation(id);
    }

    @Then("the location {string} should no longer be retrievable")
    public void verifyLocationDeleted(String id) {
        assertNull(getLocManager().readLocation(id));
    }

    // --- DUPLICATE ---
    @When("I attempt to create a location with ID {string} and name {string}")
    public void attemptCreateDuplicate(String id, String name) {
        try {
            getLocManager().createLocation(id, name);
        } catch (IllegalArgumentException e) {
            this.lastException = e;
        }
    }

    @Then("the system should not create the second location")
    public void verifyNoDuplicateCreated() {
        assertNotNull(lastException);
        assertTrue(lastException.getMessage().contains("already exists"));
    }

    @Then("the location {string} should still have the name {string}")
    public void verifyLocationNameUnchanged(String id, String name) {
        assertEquals(name, getLocManager().readLocation(id).getName());
    }

    // --- SHARED HELPER (Keep this here as it creates Locations) ---
    @Given("a location exists with ID {string} and name {string}")
    public void setupExistingLocation(String id, String name) {
        getLocManager().createLocation(id, name);
    }

    // *** REMOVED: addCharger method ***
    // *** REMOVED: verifyChargerInLocation method ***
    // (These are now in StepDefinitionsCharger.java)
}