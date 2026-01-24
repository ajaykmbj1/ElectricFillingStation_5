package org.example;

import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private Map<String, Customer> customers = new HashMap<>();

    public void createCustomer(Customer customer) {
        customers.put(customer.getCustomerID(), customer);

    }

    public Customer createCustomerandReturn(String customerID, String name, double initialBalance) {
        Customer customer = Customer.create(customerID, name, initialBalance);
        customers.put(customer.getCustomerID(), customer);

        return customer;
    }

    public void topUpBalance(String customerId, double amount) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        double newBalance = customer.getBalance() + amount;

        customer.updateBalance(newBalance);
    }

    public Customer readCustomer(String id) {
        return customers.get(id);
    }

    public void updateCustomer(Customer customer) {
        customers.put(customer.getCustomerID(), customer);
    }

    public void deleteCustomer(String id) {
        customers.remove(id);
    }
}