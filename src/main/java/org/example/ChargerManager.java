package org.example;

import java.util.HashMap;
import java.util.Map;

public class ChargerManager {
    private Map<String, Charger> chargers = new HashMap<>();

    public Charger createCharger(String id, String type) {
        if (chargers.containsKey(id)) {
            throw new IllegalArgumentException("Charger ID already exists: " + id);
        }
        Charger charger = Charger.create(id, type);
        chargers.put(id, charger);
        return charger;
    }

    public Charger readCharger(String id) {
        return chargers.get(id);
    }

    public void updateCharger(Charger charger) {
        if (!chargers.containsKey(charger.getId())) {
            throw new IllegalArgumentException("Charger not found for update: " + charger.getId());
        }
        chargers.put(charger.getId(), charger);
    }
    public void deleteCharger(String id) {
        if (chargers.remove(id) == null) {
            System.out.println("Warning: Charger with ID " + id + " was not found to delete.");
        } else {
            System.out.println("Charger " + id + " successfully deleted.");
        }
    }
}