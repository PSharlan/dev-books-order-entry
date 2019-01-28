package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entities.Address;
import com.netcracker.sharlan.entities.Customer;
import com.netcracker.sharlan.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@PathVariable long id) {
        return customerService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Customer updatedCustomer(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable long id) {
        customerService.delete(id);
    }

}
