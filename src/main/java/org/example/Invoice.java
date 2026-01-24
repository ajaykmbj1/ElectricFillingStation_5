package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Invoice {
    private String invoiceID;
    private double totalAmount;
    private LocalDateTime date;


    public Invoice(String invoiceID, double totalAmount) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        date = LocalDateTime.now();
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public LocalDateTime getDate() {return date;}

    @Override
    public String toString() {
        return "Invoice{ID='" + invoiceID + "', Amount=" + totalAmount + "', Date=" + date + "}";
    }
}