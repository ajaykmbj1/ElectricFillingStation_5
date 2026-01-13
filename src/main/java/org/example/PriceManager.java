package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class PriceManager {
    // Maps a Location ID to a list of its Prices
    private Map<String, List<Price>> locationPrices = new HashMap<>();

    // Requirement: setPrice()
    public void setPrice(String locationID, Price price) {
        locationPrices.computeIfAbsent(locationID, k -> new ArrayList<>()).add(price);
    }

    // Requirement: readCurrentPrices()
    // Returns the latest valid price for a specific location and mode
    public Price readCurrentPrice(String locationID, ChargerType mode) {
        List<Price> prices = locationPrices.get(locationID);
        if (prices == null) return null;

        // Logic: Find prices matching the mode, sort by date (newest first), pick first
        return prices.stream()
                .filter(p -> p.getMode() == mode)
                // Filter out future prices if necessary, for MVP we take the newest
                .sorted((p1, p2) -> p2.getValidFrom().compareTo(p1.getValidFrom()))
                .findFirst()
                .orElse(null);
    }

    // Helper for all prices at a location
    public List<Price> readAllPrices(String locationID) {
        return locationPrices.getOrDefault(locationID, Collections.emptyList());
    }
}