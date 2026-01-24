package org.example;

import java.util.*;

public class PriceManager {
    private Map<String, List<Price>> locationPrices = new HashMap<>();

    public void setPrice(String locationID, Price price) {
        locationPrices.computeIfAbsent(locationID, k -> new ArrayList<>()).add(price);
    }


    public Price readCurrentPrice(String locationID, ChargerType mode) {
        List<Price> prices = locationPrices.get(locationID);
        if (prices == null) return null;

        return prices.stream()
                .filter(p -> p.getType() == mode)
                .sorted((p1, p2) -> p2.getValidFrom().compareTo(p1.getValidFrom()))
                .findFirst()
                .orElse(null);
    }

    public List<Price> readAllPrices(String locationID) {
        return locationPrices.getOrDefault(locationID, Collections.emptyList());
    }
}