package com.netcracker.sharlan.service;

import com.netcracker.sharlan.entities.Order;

import java.util.Set;

public interface OrderService {

    Order save(Order order);

    Set<Order> findAllOrders();

    Order findById(long id);

    Order update(Order order);

    void delete(Order order);

    void delete(long id);
}
