package org.example;

import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private Map<String, Customer> customers = new HashMap<>();

    public Customer createCustomer(String id, String name, double balance) {
        Customer c = Customer.create(id, name, balance);
        customers.put(id, c);
        return c;
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