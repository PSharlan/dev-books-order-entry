package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.OrderDao;
import com.netcracker.sharlan.entities.Order;
import com.netcracker.sharlan.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao){
        this.orderDao = orderDao;
    }

    @Override
    public Order save(Order order) {
        for (OrderItem orderItem : order.getItems()) {
            orderItem.setOrder(order);
        }
        return orderDao.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(long id) {
        return orderDao.findById(id).orElse(null);
    }

    @Override
    public List<Order> findByCustomerId(long customerId) {
        return orderDao.findByCustomerId(customerId);
    }

    @Override
    public List<Order> findCustomerOrdersByCategory(long customerId, String category) {
        List<Order> allOrders = orderDao.findByCustomerId(customerId);
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order: allOrders) {
            for (OrderItem item : order.getItems()) {
                if (item.getCategory().equals(category)){
                    filteredOrders.add(order);
                    break;
                }
            }
        }
        return filteredOrders;
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Override
    public void delete(long id) {
        orderDao.deleteById(id);
    }
}
