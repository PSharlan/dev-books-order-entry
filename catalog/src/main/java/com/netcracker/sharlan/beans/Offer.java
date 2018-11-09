package com.netcracker.sharlan.beans;

import java.util.Objects;

public class Offer {

    private int id;
    private String description;
    private Category category;
    private double price;

    /**
     * Case: creation of new Offer before insert
     */
    public Offer(String description, Category category, double price) {
        this.description = description;
        this.category = category;
        this.price = price;
    }

    /**
     * Case: creation of already existing at database Offer
     */
    public Offer(int id, String description, Category category, double price) {
        this(description, category, price);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Double.compare(offer.price, price) == 0 &&
                Objects.equals(description, offer.description) &&
                category == offer.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, category, price);
    }
}
