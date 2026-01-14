package org.example;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsCustomer {

    private CustomerManager customerManager = new CustomerManager();
    private Customer lastReadCustomer;
    private Exception lastError;

    @Given("the Customer Manager is ready")
    public void the_customer_manager_is_ready() {
        // Reset manager for each scenario
        customerManager = new CustomerManager();
    }

    @When("I register a customer with ID {string}, name {string}, and balance {double}")
    public void i_register_a_customer(String id, String name, double balance) {
        // Matching your Customer.java constructor/factory
        // Assuming: new Customer() -> create() or similar logic.
        // Based on bytecode, you likely have a factory or constructor.
        // I will use the Constructor pattern which fits most simple POJOs:

        // IF your class uses a static create method, change this line!
        // But standard POJO usually allows:

        // We set fields via the helper method 'create' seen in bytecode or setters
        // Bytecode showed a 'create' method in Customer class:
        Customer customer = Customer.create(id, name, balance);

        customerManager.createCustomer(customer);
    }

    @Then("the customer {string} should exist")
    public void the_customer_should_exist(String id) {
        Customer c = customerManager.readCustomer(id);
        assertNotNull(c, "Customer " + id + " should exist but was null");
    }

    @Then("the customer {string} should have a balance of {double}")
    public void the_customer_should_have_balance(String id, double expectedBalance) {
        Customer c = customerManager.readCustomer(id);
        assertNotNull(c);
        assertEquals(expectedBalance, c.getBalance(), 0.01);
    }

    @Then("I can read the details of customer {string}")
    public void i_can_read_details(String id) {
        lastReadCustomer = customerManager.readCustomer(id);
        assertNotNull(lastReadCustomer);
    }

    @Then("the customer name should be {string}")
    public void the_customer_name_should_be(String expectedName) {
        assertNotNull(lastReadCustomer);
        assertEquals(expectedName, lastReadCustomer.getName());
    }

    @When("I update the name of customer {string} to {string}")
    public void i_update_customer_name(String id, String newName) {
        Customer c = customerManager.readCustomer(id);
        assertNotNull(c, "Cannot update non-existent customer");

        // Based on bytecode: updateName(String)
        Customer updated = c.updateName(newName);
        customerManager.updateCustomer(updated);
    }

    @Then("the customer name for {string} should be {string}")
    public void verify_updated_name(String id, String expectedName) {
        Customer c = customerManager.readCustomer(id);
        assertEquals(expectedName, c.getName());
    }

    @When("I top up the balance of {string} by {double}")
    public void i_top_up_balance(String id, double amount) {
        // Based on bytecode: topUpBalance(String id, double amount)
        customerManager.topUpBalance(id, amount);
    }

    @When("I delete the customer {string}")
    public void i_delete_customer(String id) {
        customerManager.deleteCustomer(id);
    }

    @Then("the customer {string} should no longer exist")
    public void customer_should_not_exist(String id) {
        Customer c = customerManager.readCustomer(id);
        assertNull(c, "Customer " + id + " was supposed to be deleted but was found.");
    }
}