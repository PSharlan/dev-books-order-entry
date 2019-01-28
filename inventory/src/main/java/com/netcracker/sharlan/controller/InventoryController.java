package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entities.Order;
import com.netcracker.sharlan.entities.OrderItem;
import com.netcracker.sharlan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @RequestMapping(value = "/orders", params = "customerId", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrdersByCustomerId(@RequestParam long customerId) {
        return orderService.findByCustomerId(customerId);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(@PathVariable long id) {
        return orderService.findById(id);
    }

    @RequestMapping(value = "/orders", params = {"customerId", "category"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrdersByCategory(@RequestParam long customerId, @RequestParam String category) {
        return orderService.findCustomerOrdersByCategory(customerId, category);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Order updatedOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable long id) {
        orderService.delete(id);
    }


}
