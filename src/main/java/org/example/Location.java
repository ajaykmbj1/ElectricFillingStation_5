
package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Location {
    private String id;
    private String name;
    private List<Charger> chargers = new ArrayList<>();


    public static Location create(String id, String name) {
        Location loc = new Location();
        loc.id = id;
        loc.name = name;
        return loc;
    }

    public Location updateName(String newName) {
        this.name = newName;
        return this;
    }

    public Location addCharger(Charger charger) {
        this.chargers.add(charger);
        return this;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Charger> getChargers() { return chargers; }

    @Override
    public String toString() {
        String chargerList = chargers.isEmpty() ? "None" :
                chargers.stream().map(Charger::toString).collect(Collectors.joining(", "));
        return "Location{ID='" + id + "', Name='" + name + "', Chargers=[" + chargerList + "]}";
    }
}
