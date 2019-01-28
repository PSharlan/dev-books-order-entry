package com.netcracker.sharlan.service;

import com.netcracker.sharlan.entities.Order;

import java.util.List;
import java.util.Set;

public interface OrderService {

    Order save(Order order);

    List<Order> findAll();

    Order findById(long id);

    List<Order> findByCustomerId(long customerId);

    List<Order> findCustomerOrdersByCategory(long customerId, String category);

    void delete(Order order);

    void delete(long id);
}
