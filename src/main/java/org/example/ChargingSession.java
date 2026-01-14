package org.example;

import java.util.Date;

public class ChargingSession {
    private String sessionID;
    private Date startTime;
    private Date endTime;
    private ChargerType charger;

    public ChargingSession(String sessionID, ChargerType charger) {
        this.sessionID = sessionID;
        this.startTime = new Date(); // Sets time to NOW
        this.charger = charger;
    }

    // Setters for process flow
    public void setEndTime(Date endTime) { this.endTime = endTime; }


    // Getters
    public String getSessionID() { return sessionID; }
    public Date getStartTime() { return startTime; }
    public Date getEndTime() { return endTime; }
    public ChargerType getCharger() { return charger; }
    //returns zero when is still charging
    public double getChargedKwh() {
        if (endTime == null) {
            return 0;
        }

        if (charger == ChargerType.DC) {
            long diffInMillis = endTime.getTime() - startTime.getTime();
            double diffInMin = diffInMillis / (60.0 * 1000.0);

            return diffInMin * 10;
        }
        long diffInMillis = endTime.getTime() - startTime.getTime();
        double diffInMin = diffInMillis / (60.0 * 1000.0);

        return diffInMin * 5;

    }


    // Helper to calculate duration in minutes
    public double getDurationInMinutes() {
        if (endTime == null || startTime == null) return 0.0;
        long diff = endTime.getTime() - startTime.getTime();
        // milliseconds -> minutes
        return diff / (60.0 * 1000.0);
    }
}