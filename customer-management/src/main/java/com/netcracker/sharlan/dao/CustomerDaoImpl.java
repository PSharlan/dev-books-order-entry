package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.entities.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityTransaction;
import java.util.HashSet;
import java.util.Set;

@Repository
public class CustomerDaoImpl extends AbstractDao<Customer> implements CustomerDao{

    private EntityTransaction transaction;

    public CustomerDaoImpl(){
        super(Customer.class);
    }

    @Override
    public Customer findById(long id) {
        return findOneById(id);
    }

    @Override
    public Set<Customer> findAllCustomers(){
        return new HashSet<Customer>(findAll());
    }

    @Override
    public Set<Customer> findByName(String name) {
        HashSet<Customer> set = new HashSet<Customer>(getEntityManager().createQuery("select c from Customer c where c.name = " + name).getResultList());
        return set;
    }

    @Override
    public Set<Customer> findByLastName(String lastName) {
        HashSet<Customer> set = new HashSet<Customer>(getEntityManager().createQuery("select c from Customer c where c.lastName = " + lastName).getResultList());
        return set;
    }

    @Override
    public Customer save(Customer customer) {
        return create(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return merge(customer);
    }

    @Override
    public void delete(Customer customer) {
        remove(customer);
    }

    @Override
    public void delete(long id) {
        remove(findById(id));
    }
}
