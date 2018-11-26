package com.netcracker.sharlan.hibernate.dao;

import com.netcracker.sharlan.bean.Order;

import java.util.Set;

public interface OrderDao {

    Order save(Order order);

    Set<Order> findAllOrders();

    Order findById(int id);

    Order update(Order order);

    void delete(Order order);

    void delete(int id);

    void beginTransaction();

    void endTransaction();

    void cancel();
}
