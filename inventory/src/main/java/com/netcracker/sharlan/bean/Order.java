package com.netcracker.sharlan.bean;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="`order`")
public class Order extends BaseEntity{

    @Column(name="customer_id", nullable = false)
    private int customerId;

    @Column(name="items_amount")
    private int itemsAmount;

    @Column(name="price_amount")
    private double priceAmount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="payment_status")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="order_status")
    private OrderStatus orderStatus;

    @Column(name="creation_time")
    private Timestamp creation;

    @Column(name="closing_time")
    private Timestamp closing;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    /**
     * case: for frameworks
     */
    public Order(){}

    /**
     * case: creation of NEW Order without order items.
     * ItemsAmount: 0, PriceAmount: 0.0
     */
    public Order(int customerId, PaymentStatus paymentStatus, OrderStatus orderStatus) {
        this.customerId = customerId;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
    }

    /**
     * case: creation of NEW Order with order items.
     * ItemsAmount and PriceAmount will be calculated.
     */
    public Order(int customerId, PaymentStatus paymentStatus, OrderStatus orderStatus, List<OrderItem> items) {
        this(customerId, paymentStatus, orderStatus);
        this.setItems(items);
        updateItemsInfo();
    }

    public void updateItemsInfo(){
        int amount = 0;
        double price = 0;
        for (OrderItem item : items) {
            amount++;
            price += item.getPrice();
        }
        this.itemsAmount = amount;
        this.priceAmount = price;
//        this.items = items;

        //before insert items have to know about their order
        for (OrderItem item : items) {
            item.setOrder(this);
        }
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

    public Timestamp getCreation() {
        return creation;
    }

    public void setCreation(Timestamp creationTS) {
        this.creation = creationTS;
    }

    public Timestamp getClosing() {
        return closing;
    }

    public void setClosing(Timestamp closingTS) {
        this.closing = closingTS;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getId() == order.getId() &&
                customerId == order.customerId &&
                itemsAmount == order.itemsAmount &&
                Double.compare(order.priceAmount, priceAmount) == 0 &&
                paymentStatus == order.paymentStatus &&
                orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), customerId, itemsAmount, priceAmount, paymentStatus, orderStatus, creation, closing);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                ", customerId=" + customerId +
                ", itemsAmount=" + itemsAmount +
                ", priceAmount=" + priceAmount +
                ", paymentStatus=" + paymentStatus +
                ", orderStatus=" + orderStatus +
                ", creationTS=" + creation +
                ", closingTS=" + closing +
                '}';
    }
}
