package com.netcracker.sharlan.hibernate.dao;

import com.netcracker.sharlan.bean.Order;

import java.util.HashSet;
import java.util.Set;

public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    public OrderDaoImpl(){
        setPersistentClass(Order.class);
    }

    @Override
    public Order save(Order order) {
        return create(order);
    }

    @Override
    public Set<Order> findAllOrders() {
        return new HashSet<Order>(findAll());
    }

    @Override
    public Order findById(int id) {
        return findOneById(id);
    }

    @Override
    public Order update(Order order) {
        return merge(order);
    }

    @Override
    public void delete(Order order) {
        remove(order);
    }

    @Override
    public void delete(int id) {
        delete(findById(id));
    }

    @Override
    public void beginTransaction() {
        begin();
    }

    @Override
    public void endTransaction() {
        commit();
    }

    @Override
    public void cancel() {
        rollBack();
    }
}
