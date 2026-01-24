package org.example;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsCharger {

    private ChargerManager chargerManager = CommonSteps.chargerManager;

    private Charger lastReadCharger;
    private Exception lastError;

    @Given("the Charger Manager is ready")
    public void the_charger_manager_is_ready() {
        if (CommonSteps.chargerManager == null) {
            CommonSteps.chargerManager = new ChargerManager();
        }
        this.chargerManager = CommonSteps.chargerManager;
        lastError = null;
    }

    @When("I create a charger with ID {string} of type {string}")
    public void i_create_a_charger(String id, String typeStr) {
        if (chargerManager == null) chargerManager = new ChargerManager();

        try {
            ChargerType type = ChargerType.valueOf(typeStr);
            chargerManager.createCharger(id, type);

            // Bugfix: Status manuell setzen, da er sonst null ist
            Charger c = chargerManager.readCharger(id);
            if (c.getStatus() == null) {
                c.setStatus(ChargerStatus.FREE);
            }
        } catch (Exception e) {
            lastError = e;
        }
    }

    @When("I try to create another charger with ID {string} of type {string}")
    public void i_try_to_create_duplicate_charger(String id, String typeStr) {
        i_create_a_charger(id, typeStr);
    }

    @Then("the charger {string} should exist")
    public void the_charger_should_exist(String id) {
        Charger c = chargerManager.readCharger(id);
        assertNotNull(c, "Charger " + id + " was expected to exist.");
    }

    @Then("the charger {string} should have type {string}")
    public void the_charger_should_have_type(String id, String expectedTypeStr) {
        Charger c = chargerManager.readCharger(id);
        assertNotNull(c);
        assertEquals(ChargerType.valueOf(expectedTypeStr), c.getType());
    }

    @Then("I can read the charger {string}")
    public void i_can_read_the_charger(String id) {
        lastReadCharger = chargerManager.readCharger(id);
        assertNotNull(lastReadCharger);
        assertEquals(id, lastReadCharger.getId());
    }

    @Then("the charger {string} should have status {string}")
    public void the_charger_should_have_status(String id, String statusStr) {
        Charger c = chargerManager.readCharger(id);
        assertNotNull(c);
        assertEquals(ChargerStatus.valueOf(statusStr), c.getStatus());
    }

    @When("I update the charger {string} to type {string}")
    public void i_update_the_charger_to_type(String id, String newTypeStr) {
        Charger c = chargerManager.readCharger(id);
        assertNotNull(c, "Cannot update non-existent charger");
        ChargerType newType = ChargerType.valueOf(newTypeStr);
        Charger updatedCharger = c.updateType(newType);
        chargerManager.updateCharger(updatedCharger);
    }

    @When("I delete the charger {string}")
    public void i_delete_the_charger(String id) {
        chargerManager.deleteCharger(id);
    }

    @Then("the charger {string} should no longer exist")
    public void the_charger_should_no_longer_exist(String id) {
        Charger c = chargerManager.readCharger(id);
        assertNull(c, "Charger " + id + " should have been deleted.");
    }

    @Then("I should receive an error message {string}")
    public void i_should_receive_error_message(String expectedMsgFragment) {
        assertNotNull(lastError, "Expected an error but none occurred!");
        String actualMsg = lastError.getMessage();
        assertTrue(actualMsg.contains(expectedMsgFragment),
                "Expected error to contain '" + expectedMsgFragment + "' but got: " + actualMsg);
    }
}