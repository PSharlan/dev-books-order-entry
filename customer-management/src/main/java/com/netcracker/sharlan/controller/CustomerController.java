package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entities.Customer;
import com.netcracker.sharlan.exception.EntityNotFoundException;
import com.netcracker.sharlan.exception.EntityNotUpdatedException;
import com.netcracker.sharlan.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/customers")
@Api(value = "/api/v1/customers", description = "Manage customers")
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class.getName());

    @Autowired
    CustomerService customerService;

    @ApiOperation(
            value = "Create customer",
            notes = "Required customer instance"
    )
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(
            @ApiParam(value = "Customer instance", required = true)
            @RequestBody Customer customer) {
        LOGGER.info("Saving customer: " + customer);
        Customer savedCustomer = customerService.save(customer);
        LOGGER.info("Saved customer: " + savedCustomer + " with id: " + savedCustomer.getId());
        return savedCustomer;
    }

    @ApiOperation(value = "Return all existing customers")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Customer> getAllCustomers() {
        LOGGER.info("Searching for all customers");
        Set<Customer> foundCustomers = customerService.findAll();
        LOGGER.info("Found customers: " + foundCustomers);
        return foundCustomers;
    }

    @ApiOperation(value = "Return customer by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(
            @ApiParam(value = "Id of a customer to lookup for", required = true)
            @PathVariable long id) {
        LOGGER.info("Searching for a customer by id: " + id);
        Customer foundCustomer = customerService.findById(id);
        if(foundCustomer == null) {
            LOGGER.info("Customer not found");
            throw new EntityNotFoundException(Customer.class, id);
        }
        LOGGER.info("Found customer: " + foundCustomer);
        return foundCustomer;
    }

    @ApiOperation(value = "Return customer by email")
    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerByEmail(
            @ApiParam(value = "Customer email", required = true)
            @PathVariable String email) {
        LOGGER.info("Searching for a customer by email: " + email);
        Customer foundCustomer = customerService.findByEmail(email);
        if(foundCustomer == null) {
            LOGGER.info("Customer not found");
            throw new EntityNotFoundException(Customer.class, email);
        }
        LOGGER.info("Found customer: " + foundCustomer);
        return foundCustomer;
    }

    @ApiOperation(
            value = "Update customer",
            notes = "Required customer instance"
    )
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Customer updatedCustomer(
            @ApiParam(value = "Customer instance", required = true)
            @RequestBody Customer customer) {
        LOGGER.info("Updating customer: " + customer);
        Customer updatedCustomer = customerService.update(customer);
        if(updatedCustomer == null) {
            LOGGER.info("Can not update not existing customer");
            throw new EntityNotUpdatedException(Customer.class, customer.getId());
        }
        LOGGER.info("Updated customer: " + updatedCustomer);
        return updatedCustomer;
    }

    @ApiOperation(value = "Delete customer by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(
            @ApiParam(value = "Id of a customer to delete", required = true)
            @PathVariable long id) {
        LOGGER.info("Deleting customer by id: " + id);
        customerService.delete(id);
        LOGGER.info("Customer deleted");
    }

}
