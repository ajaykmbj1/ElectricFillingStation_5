package org.example;

import java.util.Date;

public class Price {
    private String priceID;
    private ChargerType mode; // AC or DC
    private double performanceKwh; // Price per kWh (e.g., 0.50)
    private double durationMin;    // Price per Minute (e.g., 0.05)
    private Date validFrom;

    public Price(String priceID, ChargerType mode, double performanceKwh, double durationMin, Date validFrom) {
        this.priceID = priceID;
        this.mode = mode;
        this.performanceKwh = performanceKwh;
        this.durationMin = durationMin;
        this.validFrom = validFrom;
    }

    // Getters
    public String getPriceID() { return priceID; }
    public ChargerType getMode() { return mode; }
    public double getPerformanceKwh() { return performanceKwh; }
    public double getDurationMin() { return durationMin; }
    public Date getValidFrom() { return validFrom; }

    @Override
    public String toString() {
        return "Price{ID='" + priceID + "', Mode=" + mode + ", perKwh=" + performanceKwh + ", perMin=" + durationMin + "}";
    }
}