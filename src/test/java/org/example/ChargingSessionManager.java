package org.example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChargingSessionManager {
    private Map<String, ChargingSession> sessions = new HashMap<>();

    public ChargingSession startSession(String sessionID, Charger charger, Customer customer) {
        if (charger.getStatus() != ChargerStatus.FREE) {
            throw new IllegalStateException("Charger is occupied/out of order");
        }

        ChargingSession session = new ChargingSession(sessionID, charger.getType());
        charger.setStatus(ChargerStatus.OCCUPIED);

        sessions.put(sessionID, session);
        return session;
    }

    public void stopSession(String sessionID) {
        ChargingSession session = sessions.get(sessionID);
        if (session != null) {
            session.setEndTime(new Date()); // Set end time to now
        }
    }

    public ChargingSession readSession(String sessionID) {
        return sessions.get(sessionID);
    }
}