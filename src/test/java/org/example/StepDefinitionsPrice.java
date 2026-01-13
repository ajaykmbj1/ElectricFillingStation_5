package org.example;

import io.cucumber.java.en.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsPrice {

    private PriceManager getPriceManager() { return CommonSteps.priceManager; }

    @When("I set the price at location {string} to {double} per kWh and {double} per min for {string}")
    public void setLocationPrice(String locID, double kwhPrice, double minPrice, String typeStr) {
        ChargerType type = ChargerType.valueOf(typeStr);
        // Create Price Object (ID generated using timestamp to ensure uniqueness)
        String priceId = "PRICE-" + System.currentTimeMillis();
        Price p = new Price(priceId, type, kwhPrice, minPrice, new Date());

        getPriceManager().setPrice(locID, p);
    }

    @Then("the current {string} price at {string} should be {double} per kWh")
    public void verifyPriceKwh(String typeStr, String locID, double expectedKwh) {
        ChargerType type = ChargerType.valueOf(typeStr);
        Price p = getPriceManager().readCurrentPrice(locID, type);

        assertNotNull(p, "No price found for location " + locID + " and type " + typeStr);
        assertEquals(expectedKwh, p.getPerformanceKwh(), 0.001, "kWh Price mismatch");
    }

    @Then("the current {string} price at {string} should be {double} per min /(duration)")
    public void verifyPriceDuration(String typeStr, String locID, double expectedMin) {
        ChargerType type = ChargerType.valueOf(typeStr);
        Price p = getPriceManager().readCurrentPrice(locID, type);

        assertNotNull(p, "No price found for location " + locID + " and type " + typeStr);
        assertEquals(expectedMin, p.getDurationMin(), 0.001, "Minute Price mismatch");
    }
}