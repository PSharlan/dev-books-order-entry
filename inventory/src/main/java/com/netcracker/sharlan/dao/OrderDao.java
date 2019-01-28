package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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
