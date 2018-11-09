package com.netcracker.sharlan.beans;

public class Customer {

    private int id;
    private String name;
    private String lastName;
    private String email;
    private Address address;

    public Customer(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public Customer(String name, String lastName, String email, Address address) {
        this(name, lastName, email);
        this.address = address;
    }

    public Customer(int id, String name, String lastName, String email, Address address) {
        this(name, lastName, email, address);
        this.id = id;
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
}
