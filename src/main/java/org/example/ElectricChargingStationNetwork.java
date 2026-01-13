package org.example;

public class ElectricChargingStationNetwork {

    public static void main(String[] args) {
        System.out.println("=== Manager Demo ===");

        // Manager instanziieren
        LocationManager locManager = new LocationManager();
        ChargerManager chgManager = new ChargerManager();
        CustomerManager custManager = new CustomerManager();

        // --- LOCATION ---
        System.out.println("\n--- Location Management ---");
        Location loc = locManager.createLocation("LOC-01", "Vienna Central");
        System.out.println("Created: " + locManager.readLocation("LOC-01"));

        // Update
        loc.updateName("Vienna Westbahnhof"); // Objekt ändern
        locManager.updateLocation(loc);       // Im Manager speichern
        System.out.println("Updated: " + locManager.readLocation("LOC-01"));

        // --- CHARGER ---
        System.out.println("\n--- Charger Management ---");
        Charger chg = chgManager.createCharger("CHG-100", "HPC");
        // Charger der Location hinzufügen (Verbindung herstellen)
        loc.addCharger(chg);
        System.out.println("Charger Created & Added: " + chg);

        // --- CUSTOMER ---
        System.out.println("\n--- Customer Management ---");
        Customer cust = custManager.createCustomer("CUST-99", "Max Mustermann", 50.00);
        System.out.println("Customer: " + cust);

        // Balance ändern
        cust.updateBalance(40.50); // Simuliert Bezahlung
        custManager.updateCustomer(cust);
        System.out.println("Customer after usage: " + custManager.readCustomer("CUST-99"));

        // --- DELETE ---
        System.out.println("\n--- Deletion ---");
        locManager.deleteLocation("LOC-01");
        if(locManager.readLocation("LOC-01") == null) {
            System.out.println("Location LOC-01 successfully deleted.");
        }
    }
}
