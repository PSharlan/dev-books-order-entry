package com.netcracker.sharlan.bean;

import java.util.Objects;

public class Offer {

    private int id;
    private String name;
    private String description;
    private Category category;
    private double price;

    /**
     * Case: creation of new Offer before insert
     */
    public Offer(String name, String description, Category category, double price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    /**
     * Case: creation of already existing at database Offer
     */
    public Offer(int id, String name, String description, Category category, double price) {
        this(name, description, category, price);
        this.id = id;
    }

    /**
     * case: for framework
     */
    public Offer(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
