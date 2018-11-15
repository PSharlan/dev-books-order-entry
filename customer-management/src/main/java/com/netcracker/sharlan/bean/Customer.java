package com.netcracker.sharlan.bean;

import java.util.Objects;

public class Customer {

    private int id;
    private String name;
    private String lastName;
    private String email;
    private Address address;

    /**
     * Creation of new Customer before insert
     * Case: user did not indicated address
     */
    public Customer(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Case: creation of new Customer before insert
     */
    public Customer(String name, String lastName, String email, Address address) {
        this(name, lastName, email);
        this.address = address;
    }

    /**
     * Case: creation of already existing at database Customer
     */
    public Customer(int id, String name, String lastName, String email, Address address) {
        this(name, lastName, email, address);
        this.id = id;
    }

    /**
     * case: for frameworks
     */
    public Customer(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, email, address);
    }
}
