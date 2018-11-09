package com.netcracker.sharlan.beans;

public class Offer {

    private int id;
    private String description;
    private Category category;
    private double price;

    public Offer(String description, Category category, double price) {
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public Offer(int id, String description, Category category, double price) {
        this(description, category, price);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
