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
        System.out.println("setUp()");


        items = new ArrayList<>();
        items.add(new OrderItem(5, "firstOffer", "some description", "category1", 111.11));
        items.add(new OrderItem(2, "secondOffer", "some description", "category2", 222.2));
        items.add(new OrderItem(15, "thirdOffer", "some description", "category1", 321.3));

        order = new Order(3, PaymentStatus.BILLED, OrderStatus.CLOSED, items);

        System.out.println("----- Test order: " + order);
        System.out.println("----- Test items: " + order.getItems());

        orderDao = new OrderDaoImpl();
        orderDao.beginTransaction();
    }

    @AfterEach
    public void end(){
        System.out.println("end()");
        orderDao.cancel();
    }


    @Test
    public void testSaveOrder() {
        System.out.println("->testSaveOffer()");

        Order savedOrder = orderDao.save(order);

        System.out.println("Saved order: " + savedOrder);
        System.out.println("Saved order items: " + savedOrder.getItems());

        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getItems());
    }

    @Test
    public void testFindAllOrders() {
        System.out.println("->testFindAllOrders()");
        Order savedOrder1 = orderDao.save(order);
        Order savedOrder2 = orderDao.save(order);
        Order savedOrder3 = orderDao.save(order);
        Set<Order> foundOrders = orderDao.findAllOrders();
        for (Order order : foundOrders) {
            System.out.println("----- Found order: " + order);
        }

        assertNotNull(foundOrders.size());
    }

    @Test
    public void testFindOrderById() {
        System.out.println("->testFindOrderById()");
        Order savedOrder = orderDao.save(order);
        Order foundOrder = orderDao.findById(savedOrder.getId());

        assertEquals(savedOrder, foundOrder);
    }

    @Test
    public void testUpdateOrder() {
        System.out.println("->testUpdateOrder()");
        Order savedOrder = orderDao.save(order);
        System.out.println("----- Saved order: " + savedOrder);
        int savedCustomerId = savedOrder.getCustomerId();

        savedOrder.setCustomerId(10);
        Order updatedOrder = orderDao.update(savedOrder);
        System.out.println("----- Updated order: " + updatedOrder);
        int updatedCustomerId = updatedOrder.getCustomerId();

        assertNotNull(updatedOrder);
        assertEquals(savedOrder.getId(), updatedOrder.getId());
        System.out.println("saved customer: " + savedOrder.getCustomerId() + " | updated customer: " + updatedOrder.getCustomerId());
        assertNotEquals(savedCustomerId, updatedCustomerId);
    }

    @Test
    public void testUpdateOrderItems() {
        System.out.println("->testUpdateOrderItem()");
        Order savedOrder = orderDao.save(order);
        System.out.println("----- Saved order: " + savedOrder);
        int savedItemsAmount = savedOrder.getItemsAmount();
        double savedPriceAmount = savedOrder.getPriceAmount();
        int i = 0;
        for (OrderItem item : savedOrder.getItems()) {
            System.out.println("Item #" + ++i + " : " + item);
        }
        System.out.println("--------------");
        i=0;

        for (OrderItem item : items) {
            item.setCategory("updated category");
            item.setDescription("updated description");
            item.setName("updated name #" + ++i);
            item.setPrice(111.11);
        }
        items.add(new OrderItem(1, "new item for updated", "some description", "category5", 0.5));

        savedOrder.setItems(items);
        i=0;
        Order updatedOrder = orderDao.update(savedOrder);
        System.out.println("----- Updated order: " + updatedOrder);
        for (OrderItem item : updatedOrder.getItems()) {
            System.out.println("Updated Item #" + ++i + " : " + item);
        }

        assertNotNull(updatedOrder.getItems());
        assertNotEquals(savedItemsAmount, updatedOrder.getItemsAmount());
        assertNotEquals(savedPriceAmount, updatedOrder.getPriceAmount());
    }

    @Test
    public void testDeleteOrderById() {
        System.out.println("->testDeleteOrderById()");
        Order savedOrder = orderDao.save(order);
        System.out.println("----- Saved order: " + savedOrder);

        long id = savedOrder.getId();
        orderDao.delete(id);
        Order deletedOrder = orderDao.findById(id);

        assertNull(deletedOrder);
    }
}
