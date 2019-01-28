package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entities.Order;
import com.netcracker.sharlan.entities.OrderItem;
import com.netcracker.sharlan.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@Api(value = "/api/v1/inventory", description = "Manage customers inventory")
public class InventoryController {

    @Autowired
    OrderService orderService;

    @ApiOperation(
            value = "Create order",
            notes = "Required order instance"
    )
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(
            @ApiParam(value = "Order instance", required = true)
            @RequestBody Order order) {
        return orderService.save(order);
    }

    @ApiOperation(value = "Return all existing orders")
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @ApiOperation(value = "Return list of an orders by customer id")
    @RequestMapping(value = "customers/{customerId}/orders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrdersByCustomerId(
            @ApiParam(value = "Customer id", required = true)
            @PathVariable long customerId) {
        return orderService.findByCustomerId(customerId);
    }

    @ApiOperation(value = "Return order by id")
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(
            @ApiParam(value = "Id of an order to lookup for", required = true)
            @PathVariable long id) {
        return orderService.findById(id);
    }

    @ApiOperation(
            value = "Return customer orders by category",
            notes = "Required customer id and category name"
    )
    @RequestMapping(value = "customers/{customerId}/orders/categories/{category}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrdersByCategory(
            @ApiParam(value = "Id of a customer to lookup for", required = true)
            @PathVariable long customerId,
            @ApiParam(value = "Category name", required = true)
            @PathVariable String category) {
        return orderService.findCustomerOrdersByCategory(customerId, category);
    }

    @ApiOperation(
            value = "Update order",
            notes = "Required order instance"
    )
    @RequestMapping(value = "/orders", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Order updatedOrder(
            @ApiParam(value = "Order instance", required = true)
            @RequestBody Order order) {
        return orderService.save(order);
    }

    @ApiOperation(value = "Delete order by id")
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(
            @ApiParam(value = "Id of an order to delete", required = true)
            @PathVariable long id) {
        orderService.delete(id);
    }


}
