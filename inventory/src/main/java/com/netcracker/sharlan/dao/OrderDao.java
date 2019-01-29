package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(long customerId);

//    Order save(Order order);
//
//   Set<Order> findAllOrders();
//
//    Order findById(long id);
//
//    Order update(Order order);
//
//    void delete(Order order);
//
//    void delete(long id);

}
