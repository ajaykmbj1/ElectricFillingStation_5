package org.example;

import io.cucumber.java.en.*;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsInvoice {

    // Access Shared Managers
    private InvoiceManager getInvoiceManager() { return CommonSteps.invoiceManager; }
    private ChargingSessionManager getSessionManager() { return CommonSteps.sessionManager; }
    private ChargerManager getChgManager() { return CommonSteps.chargerManager; }
    private CustomerManager getCustManager() { return CommonSteps.customerManager; }
    private PriceManager getPriceManager() { return CommonSteps.priceManager; }

    private Invoice lastInvoice;

    // --- GIVEN (SETUP) ---

    @Given("a valid session {string} exists at {string} with {double} kWh and {double} minutes duration")
    public void setupMockSession(String sessionID, String locID, double kwh, double minutes) {
        // 1. Determine Charger Type based on what prices are set at this location
        // (This ensures our mock session matches the price we set in the Gherkin scenario)
        ChargerType type = ChargerType.AC; // Default
        if (getPriceManager().readCurrentPrice(locID, ChargerType.DC) != null) {
            type = ChargerType.DC;
        }

        // 2. Create Dummy Objects needed for a session
        String chargerID = "CHG-MOCK-" + sessionID;
        // Check if charger exists, if not create (prevents errors if reused)
        Charger c = getChgManager().readCharger(chargerID);
        if (c == null) {
            c = getChgManager().createCharger(chargerID, type);
        }

        Customer cust = getCustManager().readCustomer("CUST-MOCK");
        if (cust == null) {
            cust = getCustManager().createCustomer("CUST-MOCK", "Mock User", 100.0);
        }

        // 3. Start Session
        // We ensure charger is free before starting mock session
        c.setStatus(ChargerStatus.FREE);
        ChargingSession s = getSessionManager().startSession(sessionID, c, cust);

        // 4. Manipulate Session Data (Simulate usage)
        s.setKwhCharged(kwh);

        // Manipulate End Time: Start Time + (Minutes * 60000 ms)
        long durationMillis = (long) (minutes * 60 * 1000);
        Date calculatedEndTime = new Date(s.getStartTime().getTime() + durationMillis);
        s.setEndTime(calculatedEndTime);
    }

    @Given("an invoice exists with ID {string} and amount {double}")
    public void setupExistingInvoice(String invID, double amount) {
        // Create an invoice directly without calculation logic
        Invoice inv = new Invoice(invID, amount, "MOCK-SESSION-ID");
        getInvoiceManager().addInvoiceDirectly(inv);
    }

    // --- WHEN (ACTIONS) ---

    @When("I generate an invoice for session {string} at location {string}")
    public void generateInvoice(String sessionID, String locID) {
        ChargingSession s = getSessionManager().readSession(sessionID);
        assertNotNull(s, "Session " + sessionID + " not found!");

        // Generate and store result
        this.lastInvoice = getInvoiceManager().createInvoice("INV-" + System.currentTimeMillis(), s, getPriceManager(), locID);
    }

    // --- THEN (VERIFICATION) ---

    @Then("the invoice amount should be {double}")
    public void verifyInvoiceAmount(double expected) {
        assertNotNull(lastInvoice, "Invoice was not generated");
        assertEquals(expected, lastInvoice.getTotalAmount(), 0.01, "Invoice total calculation incorrect");
    }

    // Handles "the invoice total should be..." (Synonym step)
    @Then("the invoice total should be {double}")
    public void verifyInvoiceTotalSynonym(double expected) {
        verifyInvoiceAmount(expected);
    }

    @Then("the invoice {string} should be retrievable")
    public void verifyInvoiceRetrieval(String invID) {
        Invoice inv = getInvoiceManager().readInvoice(invID);
        assertNotNull(inv, "Invoice " + invID + " could not be retrieved");
    }

    @Then("the system should contain at least {int} invoices")
    public void verifyInvoiceCount(int count) {
        List<Invoice> list = getInvoiceManager().readInvoices();
        assertTrue(list.size() >= count,
                "Expected at least " + count + " invoices, but found " + list.size());
    }
}