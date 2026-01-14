package org.example;

import java.util.HashMap;
import java.util.Map;

public class LocationManager {
    private Map<String, Location> locations = new HashMap<>();

    public Location createLocation(String id, String name) {
        if (locations.containsKey(id)) {
            throw new IllegalArgumentException("Location ID already exists: " + id);
        }
        Location loc = Location.create(id, name);
        locations.put(id, loc);
        return loc;
    }

    public Location readLocation(String id) {
        return locations.get(id);
    }

    public void updateLocation(Location location) {
        if (!locations.containsKey(location.getId())) {
            throw new IllegalArgumentException("Location not found for update: " + location.getId());
        }
        locations.put(location.getId(), location);
    }

    public void deleteLocation(String id) {
        if (locations.remove(id) == null) {
            System.out.println("Warning: Location with ID " + id + " was not found to delete.");
        } else {
            System.out.println("Location " + id + " deleted.");
        }
    }

}