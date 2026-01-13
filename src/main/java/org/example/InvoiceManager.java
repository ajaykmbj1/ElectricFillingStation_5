package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceManager {
    private Map<String, Invoice> invoices = new HashMap<>();

    // Requirement: readInvoices() (Returning a list for a customer or all)
    public List<Invoice> readInvoices() {
        return new ArrayList<>(invoices.values());
    }

    public Invoice readInvoice(String id) {
        return invoices.get(id);
    }

    // Logic to GENERATE the invoice using the PriceManager
    public Invoice createInvoice(String invoiceID, ChargingSession session, PriceManager priceManager, String locationID) {
        // 1. Get relevant price
        ChargerType type = session.getCharger().getType();
        Price price = priceManager.readCurrentPrice(locationID, type);

        if (price == null) {
            throw new IllegalStateException("No price defined for this location and charger type");
        }

        // 2. Calculate Costs
        double durationMins = session.getDurationInMinutes();
        // If simulation is instant, ensure at least 1 min for billing or allow 0
        if (durationMins < 0.1) durationMins = 1.0;

        double costTime = durationMins * price.getDurationMin();
        double costPower = session.getKwhCharged() * price.getPerformanceKwh();
        double total = costTime + costPower;

        // 3. Create Invoice
        Invoice invoice = new Invoice(invoiceID, total, session.getSessionID());
        invoices.put(invoiceID, invoice);
        return invoice;
    }
}