package com.netcracker.sharlan;

import com.netcracker.sharlan.bean.Order;
import com.netcracker.sharlan.bean.OrderItem;
import com.netcracker.sharlan.bean.OrderStatus;
import com.netcracker.sharlan.bean.PaymentStatus;
import com.netcracker.sharlan.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestOrderDao {

    private OrderDao orderDao;

    private Order order;
    private List<OrderItem> items;

    @BeforeEach
    public void setUp() {
        items = new ArrayList<>();
        items.add(new OrderItem(5, "firstOffer", "some description", "category1", 111.11));
        items.add(new OrderItem(2, "secondOffer", "some description", "category2", 222.2));
        items.add(new OrderItem(15, "thirdOffer", "some description", "category1", 321.3));

        order = new Order(3, PaymentStatus.BILLED, OrderStatus.CLOSED, items);

        orderDao = new OrderDaoImpl();
    }

    @AfterEach
    public void end(){
        orderDao.delete(order);
    }


    @Test
    public void testSaveOrder() {
        Order savedOrder = orderDao.save(order);

        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getItems());
    }

    @Test
    public void testFindAllOrders() {
        Order savedOrder1 = orderDao.save(order);
        Order savedOrder2 = orderDao.save(order);
        Order savedOrder3 = orderDao.save(order);
        Set<Order> foundOrders = orderDao.findAllOrders();

        assertNotNull(foundOrders.size());

        orderDao.delete(savedOrder1);
        orderDao.delete(savedOrder2);
        orderDao.delete(savedOrder3);
    }

    @Test
    public void testFindOrderById() {
        Order savedOrder = orderDao.save(order);
        Order foundOrder = orderDao.findById(savedOrder.getId());

        assertEquals(savedOrder, foundOrder);
    }

    @Test
    public void testUpdateOrder() {
        Order savedOrder = orderDao.save(order);
        int savedCustomerId = savedOrder.getCustomerId();

        savedOrder.setCustomerId(10);
        Order updatedOrder = orderDao.update(savedOrder);
        int updatedCustomerId = updatedOrder.getCustomerId();

        assertNotNull(updatedOrder);
        assertEquals(savedOrder.getId(), updatedOrder.getId());
        assertNotEquals(savedCustomerId, updatedCustomerId);
    }

    @Test
    public void testUpdateOrderItems() {
        Order savedOrder = orderDao.save(order);
        int savedItemsAmount = savedOrder.getItemsAmount();
        double savedPriceAmount = savedOrder.getPriceAmount();

        int i = 0;
        for (OrderItem item : items) {
            item.setCategory("updated category");
            item.setDescription("updated description");
            item.setName("updated name #" + ++i);
            item.setPrice(111.11);
        }
        items.add(new OrderItem(1, "new item for updated", "some description", "category5", 0.5));

        savedOrder.setItems(items);

        Order updatedOrder = orderDao.update(savedOrder);

        assertNotNull(updatedOrder.getItems());
        assertNotEquals(savedItemsAmount, updatedOrder.getItemsAmount());
        assertNotEquals(savedPriceAmount, updatedOrder.getPriceAmount());
    }

    @Test
    public void testDeleteOrderById() {
        Order savedOrder = orderDao.save(order);

        long id = savedOrder.getId();
        orderDao.delete(id);
        Order deletedOrder = orderDao.findById(id);

        assertNull(deletedOrder);
    }


}
