package org.example;

public class Invoice {
    private String invoiceID;
    private double totalAmount;

    // Optional: Reference to session for details
    private String sessionID;

    public Invoice(String invoiceID, double totalAmount, String sessionID) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.sessionID = sessionID;
    }

    public String getInvoiceID() { return invoiceID; }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return "Invoice{ID='" + invoiceID + "', Amount=" + totalAmount + "}";
    }
}