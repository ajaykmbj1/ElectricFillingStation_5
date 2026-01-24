package org.example;

import java.time.LocalDateTime;

public class Price {
    private String priceID;
    private ChargerType type;
    private double performanceKwh;
    private double durationMin;
    private LocalDateTime validFrom;

    public Price(String priceID, ChargerType mode, double performanceKwh, double durationMin) {
        this.priceID = priceID;
        this.type = mode;
        this.performanceKwh = performanceKwh;
        this.durationMin = durationMin;
        this.validFrom =  LocalDateTime.now();
    }

    public String getPriceID() { return priceID; }
    public ChargerType getType() { return type; }
    public double getPerformanceKwh() { return performanceKwh; }
    public double getDurationMin() { return durationMin; }
    public LocalDateTime getValidFrom() { return validFrom; }

    @Override
    public String toString() {
        return "Price{ID='" + priceID + "', Mode=" + type + ", perKwh=" + performanceKwh + ", perMin=" + durationMin + "}";
    }
}