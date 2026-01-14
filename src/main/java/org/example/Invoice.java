package org.example;

import java.util.Date;

public class Invoice {
    private String invoiceID;
    private double totalAmount;
    private Date date;


    public Invoice(String invoiceID, double totalAmount) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
    }

    public String getInvoiceID() { return invoiceID; }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return "Invoice{ID='" + invoiceID + "', Amount=" + totalAmount + "}";
    }
}