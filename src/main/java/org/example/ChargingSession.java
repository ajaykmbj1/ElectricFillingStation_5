package org.example;

import java.util.Date;

public class ChargingSession {
    private String sessionID;
    private Date startTime;
    private Date endTime;
    private ChargerType chargerType;
    private String locationID = "";
    private double manualKwh = 0;
    private double manualDurationMinutes = 0;

    public ChargingSession(String sessionID, ChargerType charger) {
        this.sessionID = sessionID;
        this.startTime = new Date(); // Sets time to NOW
        this.chargerType = charger;
    }
    public ChargingSession(String sessionID, String locationID, double kwh, double minutes, ChargerType type) {
        this.sessionID = sessionID;
        this.locationID = locationID;
        this.manualKwh = kwh;
        this.manualDurationMinutes = minutes;
        this.chargerType = type;
        // Wir setzen fiktive Start/Endzeiten, damit Logik nicht crasht
        this.startTime = new Date();
        this.endTime = new Date(startTime.getTime() + (long)(minutes * 60000));
    }

    // Setters for process flow
    public void setEndTime(Date endTime) { this.endTime = endTime; }
    // Getters
    public String getSessionID() { return sessionID; }
    public Date getStartTime() { return startTime; }
    public Date getEndTime() { return endTime; }
    public ChargerType getChargerType() { return chargerType; }

    public double getChargedKwh() {
        if (manualKwh != 0) {
            return manualKwh;
        }

        if (endTime == null) return 0;

        double duration = getDurationInMinutes();
        if (chargerType == ChargerType.DC) {
            return duration * 10;
        }
        return duration * 5;
    }


    // Helper to calculate duration in minutes
    public double getDurationInMinutes() {
        if (endTime == null || startTime == null) return 0.0;
        long diff = endTime.getTime() - startTime.getTime();
        // milliseconds -> minutes
        return diff / (60.0 * 1000.0);
    }
}