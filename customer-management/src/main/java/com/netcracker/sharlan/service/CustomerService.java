package com.netcracker.sharlan.service;

import com.netcracker.sharlan.entities.Customer;

import java.util.Set;

public interface CustomerService {

    Customer findById(long id);

    Set<Customer> findAll();

    Set<Customer> findByName(String name);

    Set<Customer> findByLastName(String lastName);

    Customer findByEmail(String email);

    Customer save(Customer customer);

    Customer update(Customer customer);

    void delete(Customer customer);

    void delete(long id);
}
