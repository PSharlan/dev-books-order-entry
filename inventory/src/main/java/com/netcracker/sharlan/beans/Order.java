package com.netcracker.sharlan.beans;
import java.sql.Timestamp;

public class Order {

    private int id;
    private int customerId;
    private int itemsAmount;
    private double priceAmount;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private Timestamp creationTS;
    private Timestamp closingTS;


    /**
     * Case: creation of new Order before insert
     */
    public Order(int customerId, int itemsAmount, double priceAmount, PaymentStatus paymentStatus, OrderStatus orderStatus) {
        this.customerId = customerId;
        this.itemsAmount = itemsAmount;
        this.priceAmount = priceAmount;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
    }

    /**
     * Case: creation of already existing at database Order
     */
    public Order(int id, int customerId, int itemsAmount, double priceAmount, PaymentStatus paymentStatus, OrderStatus orderStatus, Timestamp creationTS) {
        this(customerId, itemsAmount, priceAmount, paymentStatus, orderStatus);
        this.id = id;
        this.creationTS = creationTS;
    }

    /**
     * case: for frameworks
     */
    public Order(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getItemsAmount() {
        return itemsAmount;
    }

    public void setItemsAmount(int itemsAmount) {
        this.itemsAmount = itemsAmount;
    }

    public double getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(double priceAmount) {
        this.priceAmount = priceAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getCreationTS() {
        return creationTS;
    }

    public void setCreationTS(Timestamp creationTS) {
        this.creationTS = creationTS;
    }

    public Timestamp getClosingTS() {
        return closingTS;
    }

    public void setClosingTS(Timestamp closingTS) {
        this.closingTS = closingTS;
    }


}
