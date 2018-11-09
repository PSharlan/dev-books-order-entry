package com.netcracker.sharlan.beans;

public class OrderItem {

    private int orderId;
    private int orderItemId;
    private int offerId;
    private double offerPrice;

    /**
     * OrderItem can be created only if Order already exist in Database.
     * orderId and orderItemId form primary key
     */
    public OrderItem(int orderId, int orderItemId, int offerId, double offerPrice) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.offerId = offerId;
        this.offerPrice = offerPrice;
    }

    /**
     * case: for frameworks
     */
    public OrderItem(){

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }
}
