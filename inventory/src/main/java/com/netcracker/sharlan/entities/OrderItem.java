package com.netcracker.sharlan.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="order_item")
public class OrderItem extends BaseEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id", nullable = false)
    @JsonIgnore
    private Order order;

    @Column(name="offer_id")
    private int offerId;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="category")
    private String category;

    @Column(name="price")
    private double price;


    public OrderItem(int offerId, String name, String description, String category, double price) {
        this.offerId = offerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public OrderItem(Order order, int offerId, String name, String description, String category, double price) {
        this(offerId, name, description, category, price);
        this.order = order;
    }

    /**
     * case: for frameworks
     */
    public OrderItem(){

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return getId() == orderItem.getId() &&
                order == orderItem.order &&
                offerId == orderItem.offerId &&
                Double.compare(orderItem.price, price) == 0 &&
                Objects.equals(name, orderItem.name) &&
                Objects.equals(description, orderItem.description) &&
                Objects.equals(category, orderItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), order, offerId, name, description, category, price);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                ", offerId=" + offerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
