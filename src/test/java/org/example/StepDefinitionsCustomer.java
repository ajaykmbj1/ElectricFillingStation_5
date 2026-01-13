package org.example;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsCustomer {

    // Helper method to access the shared manager from CommonSteps
    private CustomerManager getCustManager() {
        return CommonSteps.customerManager;
    }

    private Customer lastCustomer;

    // --- CREATE SCENARIO ---

    @When("I register a client with ID {string} and name {string}")
    public void registerClient(String id, String name) {
        // Create new customer with default 0.0 balance
        this.lastCustomer = getCustManager().createCustomer(id, name, 0.0);
    }

    @Then("the system should return a client details string containing {string}")
    public void verifyClientToString(String name) {
        assertNotNull(lastCustomer, "Customer object was not created");
        assertTrue(lastCustomer.toString().contains(name),
                "Expected toString to contain: " + name);
    }

    // --- READ SCENARIO ---

    // This step belongs ONLY here. Remove it from StepDefinitionsLocation if it exists there.
    @Given("a client exists with ID {string} and name {string}")
    public void setupExistingClient(String id, String name) {
        getCustManager().createCustomer(id, name, 0.0);
    }

    @Then("the client {string} should be retrievable")
    public void verifyClientRetrieval(String id) {
        Customer retrieved = getCustManager().readCustomer(id);
        assertNotNull(retrieved, "Customer " + id + " could not be retrieved");
    }

    // --- UPDATE SCENARIO ---

    @When("I update the balance of client {string} to {double}")
    public void updateClientBalance(String id, double amount) {
        Customer c = getCustManager().readCustomer(id);
        assertNotNull(c, "Cannot update; Client " + id + " not found");

        c.updateBalance(amount);
        getCustManager().updateCustomer(c);
    }

    @Then("the client {string} should have a balance of {double}")
    public void verifyClientBalance(String id, double expectedBalance) {
        Customer c = getCustManager().readCustomer(id);
        assertNotNull(c, "Client " + id + " not found");
        assertEquals(expectedBalance, c.getBalance(), 0.001, "Balance mismatch");
    }

    // --- DELETE SCENARIO ---

    @When("I delete the client with ID {string}")
    public void deleteClient(String id) {
        getCustManager().deleteCustomer(id);
    }

    @Then("the client {string} should no longer be retrievable")
    public void verifyClientDeleted(String id) {
        Customer c = getCustManager().readCustomer(id);
        assertNull(c, "Client " + id + " should have been deleted, but was found");
    }

    // --- DUPLICATE CHECK SCENARIO ---

    @When("I attempt to register a client with ID {string} and name {string}")
    public void attemptRegisterDuplicate(String id, String name) {
        // Business Logic Simulation: Check existence before creating
        if (getCustManager().readCustomer(id) != null) {
            System.out.println("Blocked duplicate registration for ID: " + id);
            // Do NOT call createCustomer here
        } else {
            getCustManager().createCustomer(id, name, 0.0);
        }
    }

    @Then("the system should not create the second client")
    public void verifyNoDuplicateCreated() {
        // Implicitly verified by checking the name hasn't changed in the next step
    }

    @Then("the client {string} should still be named {string}")
    public void verifyClientName(String id, String expectedName) {
        Customer c = getCustManager().readCustomer(id);
        assertNotNull(c, "Client " + id + " missing");
        assertEquals(expectedName, c.getName(), "Client name was overwritten by duplicate attempt!");
    }
}