package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.CustomerDao;
import com.netcracker.sharlan.entities.Address;
import com.netcracker.sharlan.entities.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class.getName());

    private CustomerDao dao;

    @Autowired
    public CustomerServiceImpl(CustomerDao dao){
        this.dao = dao;
    }

    @Override
    public Customer findById(long id) {
        LOGGER.info("Searching for customer by id: " + id);
        Customer customer = dao.findById(id);
        if(customer != null) {
            customer.getAddresses().size();
            LOGGER.info("Customer addresses: " + customer.getAddresses());
        }
        LOGGER.info("Founded customer: " + customer);
        return customer;
    }

    @Override
    public Set<Customer> findAll() {
        LOGGER.info("Searching for all customers");
        Set<Customer> set = dao.findAll();
        LOGGER.info("Founded customers: " + set);
        return set;
    }

    @Override
    public Set<Customer> findByName(String name) {
        LOGGER.info("Searching for customers by name: " + name);
        Set<Customer> set = dao.findByName(name);
        LOGGER.info("Founded customers: " + set);
        return set;
    }

    @Override
    public Set<Customer> findByLastName(String lastName) {
        LOGGER.info("Searching for customers by lastName: " + lastName);
        Set<Customer> set = dao.findByName(lastName);
        LOGGER.info("Founded customers: " + set);
        return set;
    }

    @Override
    public Customer findByEmail(String email) {
        LOGGER.info("Searching for customer by email: " + email);
        Customer customer = dao.findByEmail(email);
        if(customer != null) {
            customer.getAddresses().size();
            LOGGER.info("Customer addresses: " + customer.getAddresses());
        }
        LOGGER.info("Founded customer: " + customer);
        return customer;
    }

    @Override
    public Customer save(Customer customer) {
        LOGGER.info("Saving customer : " + customer);
        for (Address address: customer.getAddresses()) {
            address.setCustomer(customer);
        }
        Customer savedCustomer = dao.save(customer);
        LOGGER.info("Saved customer : " + customer);
        return savedCustomer;
    }

    @Override
    public Customer update(Customer customer) {
        LOGGER.info("Updating customer : " + customer);
        if(dao.findById(customer.getId()) == null){
            LOGGER.info("Customer with id : " + customer.getId() + " not found. Customer not updated");
            return null;
        }
        Customer updatedCustomer = dao.update(customer);
        LOGGER.info("Updated customer : " + updatedCustomer);
        return updatedCustomer;
    }

    @Override
    public void delete(Customer customer) {
        LOGGER.info("Deleting customer : " + customer);
        dao.delete(customer);
    }

    @Override
    public void delete(long id) {
        LOGGER.info("Deleting customer by id: " + id);
        dao.delete(id);
    }
}
