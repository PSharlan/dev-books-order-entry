package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.bean.Order;

import java.util.Set;

public interface OrderDao {

    Order save(Order order);

    Set<Order> findAllOrders();

    Order findById(long id);

    Order update(Order order);

    void delete(Order order);

    void delete(long id);

}