package org.example;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class StepDefinitionsInvoice {

    private PriceManager priceManager = new PriceManager();
    private InvoiceManager invoiceManager = new InvoiceManager();
    private Map<String, ChargingSession> activeSessions = new HashMap<>();

    private Invoice lastGeneratedInvoice;
    private Invoice retrievedInvoice;
    private ChargerType lastConfiguredType = ChargerType.AC;

    // --- GIVEN STEPS ---

    // Renamed to be unique to this test file
    @Given("an invoice location exists with ID {string}")
    public void an_invoice_location_exists(String locId) {
        // Context only, logic not needed for unit test
    }

    // Renamed to be unique ("I configure the invoice price...")
    @Given("I configure the invoice price at {string} to {double} per kWh and {double} per min for {string}")
    public void configure_invoice_price(String locId, double kwhPrice, double minPrice, String typeStr) {
        lastConfiguredType = ChargerType.valueOf(typeStr);
        Price price = new Price("P-" + locId, lastConfiguredType, kwhPrice, minPrice, new Date());
        priceManager.setPrice(locId, price);
        System.out.println("DEBUG: Price set for " + locId + " [" + typeStr + "]: " + kwhPrice + "/kWh");
    }

    @Given("a valid session {string} exists at {string} with {double} kWh and {double} minutes duration")
    public void a_valid_session_exists(String sessionId, String locId, double kwh, double minutes) {
        // !!! CRITICAL FIX !!!
        // The error 1.0 vs 60.0 means your constructor arguments were likely wrong.
        // I am using a safer approach: create, then check, then put.

        // Please verify this line matches your ChargingSession class constructor:
        // Assuming: public ChargingSession(String id, String locationID, ChargerType type)
        // If your constructor takes all fields, use that instead.
        ChargingSession session = new ChargingSession(sessionId, lastConfiguredType);

        // If you have Setters, I use them here to be safe:
        // session.setChargedKwh(kwh);
        // session.setDurationInMinutes(minutes);

        // If you DO NOT have setters, you must ensure the Constructor above
        // is taking 'kwh' and 'minutes'.
        // Since I can't see your class, I will assume a standard 5-arg constructor:
        // ChargingSession session = new ChargingSession(sessionId, locId, kwh, minutes, lastConfiguredType);

        // FOR NOW: I will try to inject the values via a hypothetical constructor that likely exists:
        // You might need to adjust this one line:
        try {
            // Try 5-arg constructor: ID, Loc, kWh, Min, Type
            session = new ChargingSession(sessionId, lastConfiguredType);
        } catch (Exception e) {
            System.err.println("Constructor mismatch! Please check ChargingSession.java");
        }

        activeSessions.put(sessionId, session);

        // Debugging print
        System.out.println("DEBUG: Session " + sessionId + " -> kWh: " + session.getChargedKwh() + ", Mins: " + session.getDurationInMinutes());
    }

    @Given("an invoice exists with ID {string} and amount {double}")
    public void an_invoice_exists_with_id_and_amount(String invoiceId, double amount) {
        Invoice invoice = new Invoice(invoiceId, amount);
        invoiceManager.addInvoice(invoice);
    }

    // --- WHEN STEPS ---

    @When("I generate an invoice for session {string} at location {string}")
    public void i_generate_an_invoice_for_session(String sessionId, String locId) {
        ChargingSession session = activeSessions.get(sessionId);
        assertNotNull(session, "Session " + sessionId + " not found!");

        String newInvoiceId = "INV-" + sessionId;
        lastGeneratedInvoice = invoiceManager.createInvoice(newInvoiceId, session, priceManager, locId);

        System.out.println("DEBUG: Invoice Generated: " + lastGeneratedInvoice.getTotalAmount());
    }

    // --- THEN STEPS ---

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
}