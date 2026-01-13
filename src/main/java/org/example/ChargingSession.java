package org.example;

import java.util.Date;

public class ChargingSession {
    private String sessionID;
    private Date startTime;
    private Date endTime;

    // We need these references to know WHO is charging WHERE
    private Charger charger;
    private Customer customer;
    private double kwhCharged; // To store consumption

    public ChargingSession(String sessionID, Charger charger, Customer customer) {
        this.sessionID = sessionID;
        this.charger = charger;
        this.customer = customer;
        this.startTime = new Date(); // Sets time to NOW
    }

    // Setters for process flow
    public void setEndTime(Date endTime) { this.endTime = endTime; }
    public void setKwhCharged(double kwh) { this.kwhCharged = kwh; }

    // Getters
    public String getSessionID() { return sessionID; }
    public Date getStartTime() { return startTime; }
    public Date getEndTime() { return endTime; }
    public Charger getCharger() { return charger; }
    public double getKwhCharged() { return kwhCharged; }

    // Helper to calculate duration in minutes
    public double getDurationInMinutes() {
        if (endTime == null || startTime == null) return 0.0;
        long diff = endTime.getTime() - startTime.getTime();
        // milliseconds -> minutes
        return diff / (60.0 * 1000.0);
    }
}