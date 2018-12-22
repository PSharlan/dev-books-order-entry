package com.netcracker.sharlan;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.netcracker.sharlan.bean.Address;
import com.netcracker.sharlan.bean.Customer;
import com.netcracker.sharlan.dao.CustomerDao;
import com.netcracker.sharlan.dao.CustomerDaoImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CustomerDaoTest {

    private CustomerDao customerDao;
    private Customer customer;
    private Address singleAddress;
    private Set<Address> addresses;

    @BeforeEach
    public void setUp() {

        singleAddress = new Address("Belarus", "Minsk", "some street", 7);

        addresses = new HashSet<>();
        addresses.add(new Address("Germany", "Munich", "some street", 13));
        addresses.add(new Address("USA", "Boston", "some street", 13));
        addresses.add(new Address("Germany", "Frankfurt", "some street", 14));

        customer = new Customer("Customer Name","Customer Last Name",
                "email@gmail.com", LocalDate.of(1990, 5, 20 ));

        customerDao = new CustomerDaoImpl();

    }

    @AfterEach
    public void breakDown(){
        customerDao.delete(customer);
    }

    @Test
    public void findAllCustomers() {
        Customer c1 = new Customer("Name1", "LastName1", "email1", LocalDate.of(1990, 5, 20 ));
        Customer c2 = new Customer("Name2", "LastName2", "email2", LocalDate.of(1990, 5, 20 ));
        Customer c3 = new Customer("Name3", "LastName3", "email3", LocalDate.of(1990, 5, 20 ));

        customerDao.save(c1);
        customerDao.save(c2);
        customerDao.save(c3);

        Set<Customer> foundCustomers = customerDao.findAllCustomers();

        assertNotNull(foundCustomers);

        customerDao.delete(c1);
        customerDao.delete(c2);
        customerDao.delete(c3);
    }

    @Test
    public void findCustomerById() {
        Customer savedCustomer = customerDao.save(customer);
        Customer foundCustomer = customerDao.findById(savedCustomer.getId());

        assertEquals(savedCustomer, foundCustomer);
    }

    @Test
    public void updateCustomer() {
        Customer savedCustomer = customerDao.save(customer);
        Set<Address> savedCustomerAddress = savedCustomer.getAddresses();
        int size = savedCustomerAddress.size();
        savedCustomer.addAddress(singleAddress);

        Customer updatedCustomer = customerDao.update(savedCustomer);
        Set<Address> updatedCustomerAddress = updatedCustomer.getAddresses();

        assertNotNull(updatedCustomer);
        assertNotEquals(size, updatedCustomerAddress.size());
        assertEquals(savedCustomer.getId(), updatedCustomer.getId());
    }

    @Test
    public void deleteCustomerById() {
        Customer savedCustomer = customerDao.save(customer);

        long id = savedCustomer.getId();
        customerDao.delete(id);
        Customer deletedCustomer = customerDao.findById(id);

        assertNull(deletedCustomer);
    }


}
