package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceManager {
    private Map<String, Invoice> invoices = new HashMap<>();

    public List<Invoice> readInvoices() {
        return new ArrayList<>(invoices.values());
    }

    public Invoice readInvoice(String id) {
        return invoices.get(id);
    }

    public Invoice createInvoice(String invoiceID, ChargingSession session, PriceManager priceManager, String locationID) {
        ChargerType type = session.getCharger();
        Price price = priceManager.readCurrentPrice(locationID, type);

        if (price == null) {
            throw new IllegalStateException("No price defined for this location and charger type");
        }

        double durationMins = session.getDurationInMinutes();

        if (durationMins < 0.1) durationMins = 1.0;

        double costTime = durationMins * price.getDurationMin();
        double costPower = session.getChargedKwh() * price.getPerformanceKwh();
        double total = costTime + costPower;

        Invoice invoice = new Invoice(invoiceID, total);
        invoices.put(invoiceID, invoice);
        return invoice;
    }

    public void addInvoice(Invoice invoice) {
        invoices.put(invoice.getInvoiceID(), invoice);
    }
}