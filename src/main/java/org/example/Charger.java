package org.example;

public class Charger {
    private String id;
    private ChargerType type;
    private ChargerStatus status;

    public static Charger create(String id, ChargerType type) {
        Charger c = new Charger();
        c.id = id;
        c.type = type;
        return c;
    }

    public Charger updateType(ChargerType newType) {
        this.type = newType;
        return this;
    }
    public Charger updateCharger(ChargerStatus newStatus) {
        this.status = newStatus;
        return this;
    }

    public String getId() { return id; }
    public ChargerType getType() { return type; }
    public ChargerStatus getStatus() { return status; }

    public void setStatus(ChargerStatus chargerStatus) {
        this.status = chargerStatus;
    }

    @Override
    public String toString() {
        return "Charger{ID='" + id + "', Type='" + type + "' Status='" + status + "'};}";
    }

    public void setStatus(ChargerStatus chargerStatus) {
    }
}