package org.example;

public class CommonSteps {
    // Statische Instanzen, damit alle Tests denselben Zustand nutzen
    public static LocationManager locationManager = new LocationManager();
    public static ChargerManager chargerManager = new ChargerManager();
    public static CustomerManager customerManager = new CustomerManager();
    public static PriceManager priceManager = new PriceManager();
    public static ChargingSessionManager sessionManager = new ChargingSessionManager();
    public static InvoiceManager invoiceManager = new InvoiceManager();

    // NEU: Hier speichern wir den letzten Fehler, der aufgetreten ist
    public static Exception lastError = null;

    // Hilfsmethode zum Resetten vor jedem Szenario
    public static void reset() {
        locationManager = new LocationManager();
        chargerManager = new ChargerManager();
        customerManager = new CustomerManager();
        priceManager = new PriceManager();
        sessionManager = new ChargingSessionManager();
        invoiceManager = new InvoiceManager();
        lastError = null;
    }
}