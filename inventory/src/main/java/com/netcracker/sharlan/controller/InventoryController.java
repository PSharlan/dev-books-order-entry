package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entities.Order;
import com.netcracker.sharlan.exceptions.EntityNotFoundException;
import com.netcracker.sharlan.exceptions.EntityNotUpdatedException;
import com.netcracker.sharlan.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@Api(value = "/api/v1/inventory", description = "Manage customers inventory")
public class InventoryController {

    private static final Logger LOGGER = LogManager.getLogger(InventoryController.class.getName());

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
        LOGGER.info("Saving order: " + order);
        Order savedOrder = orderService.save(order);
        LOGGER.info("Saved order: " + order + " with id: " + order.getId());
        return savedOrder;
    }

    @ApiOperation(value = "Return all existing orders")
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders() {
        LOGGER.info("Search for all orders");
        List<Order> foundOrders = orderService.findAll();
        LOGGER.info("Found orders: " + foundOrders);
        return foundOrders;
    }

    @ApiOperation(value = "Return list of an orders by customer id")
    @RequestMapping(value = "customers/{customerId}/orders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrdersByCustomerId(
            @ApiParam(value = "Customer id", required = true)
            @PathVariable long customerId) {
        LOGGER.info("Searching for orders by customer id: " + customerId);
        List<Order> customerOrders = orderService.findByCustomerId(customerId);
        if(customerOrders == null) {
            LOGGER.info("Orders not found");
            throw new EntityNotFoundException(Order.class, customerId);
        }
        LOGGER.info("Found orders: " + customerOrders);
        return customerOrders;
    }

    @ApiOperation(value = "Return order by id")
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(
            @ApiParam(value = "Id of an order to lookup for", required = true)
            @PathVariable long id) {
        LOGGER.info("Searching for an order by id: " + id);
        Order foundOrder = orderService.findById(id);
        if(foundOrder == null) {
            LOGGER.info("Order not found");
            throw new EntityNotFoundException(Order.class, id);
        }
        LOGGER.info("Found order: " + foundOrder);
        return foundOrder;
    }

    @ApiOperation(
            value = "Return customer orders by category",
            notes = "Required customer id and category name"
    )
    @RequestMapping(value = "customers/{customerId}/orders/categories/{category}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getCustomerOrdersByCategory(
            @ApiParam(value = "Id of a customer to lookup for", required = true)
            @PathVariable long customerId,
            @ApiParam(value = "Category name", required = true)
            @PathVariable String category) {
        LOGGER.info("Searching for orders by category: " + category + " and customer id: " + customerId);
        List<Order> foundOrdrs = orderService.findCustomerOrdersByCategory(customerId, category);
        LOGGER.info("Found orders: " + foundOrdrs);
        return foundOrdrs;
    }

    @ApiOperation(
            value = "Return orders by category",
            notes = "Required category name"
    )
    @RequestMapping(value = "orders/categories/{category}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrdersByCategory(
            @ApiParam(value = "Category name", required = true)
            @PathVariable String category) {
        LOGGER.info("Searching for orders by category: " + category);
        List<Order> foundOrdrs = orderService.findOrdersByCategory(category);
        LOGGER.info("Found orders: " + foundOrdrs);
        return foundOrdrs;
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
        LOGGER.info("Updating order: " + order);
        Order updatedOrder = orderService.update(order);
        if(updatedOrder == null) {
            LOGGER.info("Can not update not existing order");
            throw new EntityNotUpdatedException(Order.class, order.getId());
        }
        LOGGER.info("Updated order: " + updatedOrder);
        return updatedOrder;
    }

    @ApiOperation(
            value = "Update order status",
            notes = "Required order id and payment status"
    )
    @RequestMapping(value = "/orders/{id}/status", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Order updatedOrderStatus(
            @ApiParam(value = "Payment status", required = true)
            @RequestBody String paymentStatus,
            @ApiParam(value = "Order id", required = true)
            @PathVariable long id) {
        LOGGER.info("Updating order status. Order id: " + id + " | New payment status: " + paymentStatus);
        Order updatedOrder = orderService.updateStatus(id, paymentStatus);
        if(updatedOrder == null) {
            LOGGER.info("Can not update not existing order or not existing status");
            throw new EntityNotUpdatedException(Order.class, id);
        }
        LOGGER.info("Updated order: " + updatedOrder);
        return updatedOrder;
    }

    @ApiOperation(value = "Delete order by id")
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(
            @ApiParam(value = "Id of an order to delete", required = true)
            @PathVariable long id) {
        LOGGER.info("Deleting order by id: " + id);
        orderService.delete(id);
        LOGGER.info("Order deleted");
    }
}
