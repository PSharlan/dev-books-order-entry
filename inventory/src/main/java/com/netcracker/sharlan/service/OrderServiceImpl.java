package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.OrderDao;
import com.netcracker.sharlan.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderDao dao;

    @Autowired
    public OrderServiceImpl(OrderDao dao){
        this.dao = dao;
    }

    @Override
    public Order save(Order order) {
        return dao.save(order);
    }

    @Override
    public Set<Order> findAllOrders() {
        return dao.findAllOrders();
    }

    @Override
    public Order findById(long id) {
        return dao.findById(id);
    }

    @Override
    public Order update(Order order) {
        return dao.update(order);
    }

    @Override
    public void delete(Order order) {
        dao.delete(order);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }
}
