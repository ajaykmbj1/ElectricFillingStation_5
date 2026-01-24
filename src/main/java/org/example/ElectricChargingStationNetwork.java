package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ElectricChargingStationNetwork {

    // Helper für professionelles Logging mit Zeitstempel
    private static void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        System.out.println(String.format("[%s] %s", timestamp, message));
    }

    public static void main(String[] args) {
        log("=== SYSTEM START: Simulation E-Ladestation ===");

        // 1. Initialisierung
        log("Initialisiere System-Manager...");
        LocationManager locManager = new LocationManager();
        ChargerManager chgManager = new ChargerManager();
        CustomerManager custManager = new CustomerManager();
        PriceManager priceManager = new PriceManager();
        ChargingSessionManager sessionManager = new ChargingSessionManager();
        InvoiceManager invoiceManager = new InvoiceManager();
        log("System bereit.");

        // 2. Infrastruktur
        log("Baue Infrastruktur auf...");
        Location location = locManager.createLocation("LOC-MAIN", "Hauptbahnhof Ladepark");
        log("Standort erstellt: " + location.getName() + " (ID: " + location.getId() + ")");

        // 3 Ladestationen erstellen
        Charger c1 = setupCharger(chgManager, location, "CH-01", ChargerType.DC);
        Charger c2 = setupCharger(chgManager, location, "CH-02", ChargerType.DC);
        Charger c3 = setupCharger(chgManager, location, "CH-03", ChargerType.DC);
        log("Infrastruktur bereit: 3 DC-Ladestationen online.");

        // 3. Kunden
        log("Registriere Kunden...");
        Customer k1 = custManager.createCustomerandReturn("K-01", "Kunde 1", 100.00);
        Customer k2 = custManager.createCustomerandReturn("K-02", "Kunde 2", 100.00);
        Customer k3 = custManager.createCustomerandReturn("K-03", "Kunde 3", 100.00);
        log("Kunden registriert: K-01 (100€), K-02 (100€), K-03 (100€).");

        // 4. Preise
        log("Konfiguriere Tarifmodell...");
        // Preis: 0.50 pro kWh, 0.10 pro Minute
        Price pOld = new Price("P-OLD-DC", ChargerType.DC, 0.50, 0.10);
        priceManager.setPrice("LOC-MAIN", pOld);
        log(String.format("Preis gesetzt für %s [DC]: %.2f EUR/kWh + %.2f EUR/min",
                "LOC-MAIN", pOld.getPerformanceKwh(), pOld.getDurationMin()));

        // ---------------------------------------------------------
        // SZENARIO A: Parallel-Laden
        // ---------------------------------------------------------
        System.out.println(); // Leerzeile
        log(">>> SZENARIO START: Paralleles Laden (Kunde 1 & 2)");

        // Session 1
        log("Kunde 1 authentifiziert an Ladestation CH-01...");
        ChargingSession s1 = sessionManager.startSession("SESS-1", c1, k1);
        log("SESS-1 gestartet. Status CH-01 geändert auf: " + c1.getStatus());

        // Session 2 (Parallel)
        log("Kunde 2 authentifiziert an Ladestation CH-02...");
        ChargingSession s2 = sessionManager.startSession("SESS-2", c2, k2);
        log("SESS-2 gestartet. Status CH-02 geändert auf: " + c2.getStatus());

        log("... Ladevorgänge laufen (Simulation) ...");
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        // Stop Session 1
        log("Kunde 1 beendet Ladevorgang...");
        sessionManager.stopSession("SESS-1");
        // Hardware-Reset simulieren (in Realität würde Hardware 'Available' senden)
        c1.setStatus(ChargerStatus.FREE);
        log("SESS-1 gestoppt. Status CH-01 zurückgesetzt auf: " + c1.getStatus());

        // Stop Session 2
        log("Kunde 2 beendet Ladevorgang...");
        sessionManager.stopSession("SESS-2");
        c2.setStatus(ChargerStatus.FREE);
        log("SESS-2 gestoppt. Status CH-02 zurückgesetzt auf: " + c2.getStatus());

        // Abrechnung K1
        processInvoice(invoiceManager, priceManager, custManager,
                "SESS-1", "LOC-MAIN", 20.0, 30.0, ChargerType.DC, k1);

        // Abrechnung K2
        processInvoice(invoiceManager, priceManager, custManager,
                "SESS-2", "LOC-MAIN", 50.0, 60.0, ChargerType.DC, k2);

        // ---------------------------------------------------------
        // SZENARIO B: Preisänderung
        // ---------------------------------------------------------
        System.out.println();
        log(">>> Owner-ACTION: Tarifänderung");
        log("Erhöhe Strompreise aufgrund von Marktlage...");

        // Kurzes Warten für Zeitstempel-Differenz
        try { Thread.sleep(20); } catch (InterruptedException e) {}

        Price pNew = new Price("P-NEW-DC", ChargerType.DC, 1.00, 0.20);
        priceManager.setPrice("LOC-MAIN", pNew);
        log(String.format("NEUER PREIS AKTIV für %s [DC]: %.2f EUR/kWh + %.2f EUR/min",
                "LOC-MAIN", pNew.getPerformanceKwh(), pNew.getDurationMin()));

        // ---------------------------------------------------------
        // SZENARIO C: Kunde 3 (Teurer Tarif)
        // ---------------------------------------------------------
        System.out.println();
        log(">>> SZENARIO START: Kunde 3 lädt zum neuen Tarif");

        log("Kunde 3 authentifiziert an Ladestation CH-03...");
        ChargingSession s3 = sessionManager.startSession("SESS-3", c3, k3);
        log("SESS-3 gestartet. Status CH-03 geändert auf: " + c3.getStatus());

        log("... Ladevorgang läuft ...");
        sessionManager.stopSession("SESS-3");
        c3.setStatus(ChargerStatus.FREE);
        log("SESS-3 gestoppt.");

        // Abrechnung K3 (Vergleich: Gleiche Menge wie K1 (20kWh/30min), aber teurer)
        processInvoice(invoiceManager, priceManager, custManager,
                "SESS-3", "LOC-MAIN", 20.0, 30.0, ChargerType.DC, k3);

        System.out.println();
        log("=== SIMULATION ERFOLGREICH BEENDET ===");
    }


    private static Charger setupCharger(ChargerManager cm, Location loc, String id, ChargerType type) {
        Charger c = cm.createCharger(id, type);
        c.setStatus(ChargerStatus.FREE);
        loc.addCharger(c);
        log("Lader erstellt: " + id + " [" + type + "]");
        return c;
    }

    private static void processInvoice(InvoiceManager im, PriceManager pm, CustomerManager cm,
                                       String sessId, String locId, double kwh, double min,
                                       ChargerType type, Customer customer) {

        System.out.println("   ------------------------------------------------");
        log("Erstelle Rechnung für " + sessId + " (" + customer.getName() + ")...");

        // Simulationsobjekt für Rechnungserstellung
        ChargingSession simResult = new ChargingSession(sessId, locId, kwh, min, type);

        double balanceBefore = customer.getBalance();
        Invoice inv = im.createInvoice("INV-" + sessId, simResult, pm, locId);

        // Kunden belasten
        double newBalance = balanceBefore - inv.getTotalAmount();
        customer.updateBalance(newBalance);
        cm.updateCustomer(customer);

        log(String.format("Rechnung %s generiert.", inv.getInvoiceID()));
        log(String.format("Verbrauch: %.2f kWh in %.0f min", kwh, min));
        log(String.format("Betrag: %.2f EUR", inv.getTotalAmount()));
        log(String.format("Konto %s: %.2f EUR -> %.2f EUR",
                customer.getCustomerID(), balanceBefore, newBalance));
        System.out.println("   ------------------------------------------------");
    }
}