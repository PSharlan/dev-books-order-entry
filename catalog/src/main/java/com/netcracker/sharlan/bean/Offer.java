package com.netcracker.sharlan.bean;

import javax.persistence.*;

@Entity
@Table(name="offer")
public class Offer {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name="category", nullable = false)
    private Category category;

    @Column(name="price", nullable = false)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
