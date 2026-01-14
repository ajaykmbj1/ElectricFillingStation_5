package org.example;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class StepDefinitionsPrice {

    private PriceManager priceManager = new PriceManager();
    private Price lastReadPrice;

    @Given("the Price Manager is ready")
    public void the_price_manager_is_ready() {
        priceManager = new PriceManager();
    }

    @When("I set the price at location {string} to {double} per kWh and {double} per min for {string}")
    public void setLocationPrice(String locId, double kwh, double min, String typeStr) {
        ChargerType type = ChargerType.valueOf(typeStr);
        // Create unique ID for the price entry
        String priceId = "P-" + locId + "-" + System.currentTimeMillis();

        Price p = new Price(priceId, type, kwh, min, new Date());
        priceManager.setPrice(locId, p);
    }

    @When("I update the price at location {string} to {double} per kWh and {double} per min for {string}")
    public void updateLocationPrice(String locId, double kwh, double min, String typeStr) throws InterruptedException {
        // Wait 10ms to ensure the Date timestamp is strictly 'later' than the previous one
        // This ensures the sorting logic in PriceManager (descending date) puts this one first.
        Thread.sleep(10);
        setLocationPrice(locId, kwh, min, typeStr);
    }

    // Simplified step for scenarios that only care about kWh
    @When("I set the price at location {string} to {double} per kWh for {string}")
    public void setLocationPriceKwhOnly(String locId, double kwh, String typeStr) {
        setLocationPrice(locId, kwh, 0.0, typeStr);
    }

    @When("I update the price at location {string} to {double} per kWh for {string}")
    public void updateLocationPriceKwhOnly(String locId, double kwh, String typeStr) throws InterruptedException {
        updateLocationPrice(locId, kwh, 0.0, typeStr);
    }

    @Then("the current {string} price at {string} should be {double} per kWh and {double} per min")
    public void verifyCurrentPrice(String typeStr, String locId, double expectedKwh, double expectedMin) {
        ChargerType type = ChargerType.valueOf(typeStr);
        lastReadPrice = priceManager.readCurrentPrice(locId, type);

        assertNotNull(lastReadPrice, "No price found for " + locId + " [" + typeStr + "]");
        assertEquals(expectedKwh, lastReadPrice.getPerformanceKwh(), 0.001);
        assertEquals(expectedMin, lastReadPrice.getDurationMin(), 0.001);
    }

    @Then("the current {string} price at {string} should be {double} per kWh")
    public void verifyCurrentPriceKwh(String typeStr, String locId, double expectedKwh) {
        ChargerType type = ChargerType.valueOf(typeStr);
        lastReadPrice = priceManager.readCurrentPrice(locId, type);

        assertNotNull(lastReadPrice, "No price found for " + locId + " [" + typeStr + "]");
        assertEquals(expectedKwh, lastReadPrice.getPerformanceKwh(), 0.001);
    }

    @Then("location {string} should have {int} prices stored")
    public void verifyPriceHistorySize(String locId, int count) {
        List<Price> history = priceManager.readAllPrices(locId);
        assertEquals(count, history.size());
    }
}