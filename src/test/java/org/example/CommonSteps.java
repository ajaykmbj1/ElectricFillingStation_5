package org.example;

import io.cucumber.java.en.Given;

public class CommonSteps {

    // Shared Managers (static so they are shared across test files)
    public static LocationManager locationManager;
    public static ChargerManager chargerManager;
    public static CustomerManager customerManager;
    public static PriceManager priceManager;
    public static InvoiceManager invoiceManager;
    public static ChargingSessionManager sessionManager;

    @Given("the Filling Station Network is available")
    public void setupNetwork() {
        // Reset everything for a fresh test scenario
        locationManager = new LocationManager();
        chargerManager = new ChargerManager();
        customerManager = new CustomerManager();
        priceManager = new PriceManager();
        invoiceManager = new InvoiceManager();
        sessionManager = new ChargingSessionManager();
    }
}