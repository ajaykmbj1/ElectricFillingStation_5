package org.example;

public class Customer {
    // Geforderte Variablen
    private String customerID;
    private String name;
    private double balance;

    private Customer() {}

    // Statische Factory Methode
    public static Customer create(String customerID, String name, double initialBalance) {
        Customer c = new Customer();
        c.customerID = customerID;
        c.name = name;
        c.balance = initialBalance;
        return c;
    }

    // --- Update Methoden (Fluent API) ---
    public Customer updateName(String name) {
        this.name = name;
        return this;
    }

    public Customer updateBalance(double newBalance) {
        this.balance = newBalance;
        return this;
    }

    // --- Getter ---
    public String getCustomerID() { return customerID; }
    public String getName() { return name; }
    public double getBalance() { return balance; }

    @Override
    public String toString() {
        return "Customer{ID='" + customerID + "', Name='" + name + "', Balance=" + balance + "}";
    }
}