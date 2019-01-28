package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.CustomerDao;
import com.netcracker.sharlan.entities.Address;
import com.netcracker.sharlan.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao dao;

    @Autowired
    public CustomerServiceImpl(CustomerDao dao){
        this.dao = dao;
    }

    @Override
    public Customer findById(long id) {
        return dao.findById(id);
    }

    @Override
    public Set<Customer> findAll() {
        return dao.findAll();
    }

    @Override
    public Set<Customer> findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public Set<Customer> findByLastName(String lastName) {
        return dao.findByLastName(lastName);
    }

    @Override
    public Customer save(Customer customer) {
        for (Address address: customer.getAddresses()) {
            address.setCustomer(customer);
        }
        return dao.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return dao.update(customer);
    }

    @Override
    public void delete(Customer customer) {
        dao.delete(customer);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }
}
