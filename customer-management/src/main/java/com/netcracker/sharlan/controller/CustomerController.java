package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entities.Address;
import com.netcracker.sharlan.entities.Customer;
import com.netcracker.sharlan.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/customers")
@Api(value = "/api/v1/customers", description = "Manage customers")
public class CustomerController {

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
        return customerService.save(customer);
    }

    @ApiOperation(value = "Return all existing customers")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @ApiOperation(value = "Return customer by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(
            @ApiParam(value = "Id of a customer to lookup for", required = true)
            @PathVariable long id) {
        return customerService.findById(id);
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
        return customerService.update(customer);
    }

    @ApiOperation(value = "Delete customer by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(
            @ApiParam(value = "Id of a customer to delete", required = true)
            @PathVariable long id) {
        customerService.delete(id);
    }

}
