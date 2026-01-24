package org.example;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.time.LocalDate; // NEU

public class StepDefinitionsInvoice {

    private PriceManager priceManager = new PriceManager();
    private InvoiceManager invoiceManager = new InvoiceManager();
    private Map<String, ChargingSession> activeSessions = new HashMap<>();

    private Invoice lastGeneratedInvoice;
    private Invoice retrievedInvoice;
    private ChargerType lastConfiguredType = ChargerType.AC;

    @Given("an invoice location exists with ID {string}")
    public void an_invoice_location_exists(String locId) { }

    @Given("I configure the invoice price at {string} to {double} per kWh and {double} per min for {string}")
    public void configure_invoice_price(String locId, double kwhPrice, double minPrice, String typeStr) {
        lastConfiguredType = ChargerType.valueOf(typeStr);
        // Konstruktor nutzt LocalDateTime.now()
        Price price = new Price("P-" + locId, lastConfiguredType, kwhPrice, minPrice);
        priceManager.setPrice(locId, price);
    }

    @Given("a valid session {string} exists at {string} with {double} kWh and {double} minutes duration and type {string}")
    public void a_valid_session_exists(String sessionId, String locId, double kwh, double minutes, String typeStr) {
        ChargerType type = ChargerType.valueOf(typeStr);
        ChargingSession session = new ChargingSession(sessionId, locId, kwh, minutes, type);
        activeSessions.put(sessionId, session);
    }

    @Given("an invoice exists with ID {string} and amount {double}")
    public void an_invoice_exists_with_id_and_amount(String invoiceId, double amount) {
        Invoice invoice = new Invoice(invoiceId, amount);
        invoiceManager.addInvoice(invoice);
    }

    @When("I generate an invoice for session {string} at location {string}")
    public void i_generate_an_invoice_for_session(String sessionId, String locId) {
        ChargingSession session = activeSessions.get(sessionId);
        assertNotNull(session, "Session " + sessionId + " not found!");
        String newInvoiceId = "INV-" + sessionId;
        lastGeneratedInvoice = invoiceManager.createInvoice(newInvoiceId, session, priceManager, locId);
    }

    @Then("the invoice amount should be {double}")
    public void the_invoice_amount_should_be(double expectedAmount) {
        assertNotNull(lastGeneratedInvoice, "No invoice was generated!");
        assertEquals(expectedAmount, lastGeneratedInvoice.getTotalAmount(), 0.01);
    }

    @Then("the invoice {string} should be retrievable")
    public void the_invoice_should_be_retrievable(String invoiceId) {
        retrievedInvoice = invoiceManager.readInvoice(invoiceId);
        assertNotNull(retrievedInvoice, "Invoice " + invoiceId + " was not found.");
        assertEquals(invoiceId, retrievedInvoice.getInvoiceID());
    }

    @Then("the invoice total should be {double}")
    public void the_invoice_total_should_be(double expectedTotal) {
        assertNotNull(retrievedInvoice);
        assertEquals(expectedTotal, retrievedInvoice.getTotalAmount(), 0.01);
    }

    @Then("the system should contain at least {int} invoices")
    public void the_system_should_contain_at_least_invoices(int minCount) {
        int count = invoiceManager.readInvoices().size();
        assertTrue(count >= minCount, "Expected at least " + minCount + " invoices but found " + count);
    }

    @Then("the invoice date should be today")
    public void the_invoice_date_should_be_today() {
        assertNotNull(lastGeneratedInvoice.getDate(), "Invoice date is null");
        LocalDate invoiceDate = lastGeneratedInvoice.getDate().toLocalDate();
        LocalDate today = LocalDate.now();
        assertEquals(today, invoiceDate, "Invoice date does not match today");
    }
}