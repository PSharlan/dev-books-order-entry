package com.netcracker.sharlan.bean;

public class OrderItem {

    private int id;
    private String name;
    private String description;
    private String category;
    private double price;
    private int orderId;


    public OrderItem(int id, String name, String description, String category, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    /**
     * orderId and id form primary key
     */
    public OrderItem(int id, String name, String description, String category, double price, int orderId) {
        this(id, name, description, category, price);
        this.orderId = orderId;
    }

    /**
     * case: for frameworks
     */
    public OrderItem(){

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
