package com.learningspring.crm.data;

import com.learningspring.crm.data.model.Customer;
import com.learningspring.crm.data.model.User;

import java.util.List;

public interface DataStorage {
    Customer getCustomerById(Long id);
    List<Customer> getAllCustomers();
    Long putCustomer(Customer customer);
    void updateCustomer(Customer customer);
    Customer deleteCustomer(Long id);
    List<Customer> getCustomers(Long from, Long count);
    Long putUser(User user);
    User getUserByUsername(String username);
}
