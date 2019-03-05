package com.netcracker.sharlan.service;

import com.netcracker.sharlan.entity.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    Order update(Order order);

    Order updateStatus(long id, String status);

    List<Order> findAll();

    Order findById(long id);

    List<Order> findByCustomerId(long customerId);

    List<Order> findOrdersByCategory(String category);

    List<Order> findCustomerOrdersByCategory(long customerId, String category);

    void delete(Order order);

    void delete(long id);
}
