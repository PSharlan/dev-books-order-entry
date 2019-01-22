package com.netcracker.sharlan.entities;
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

    @Column(name= "items_count")
    private int itemsCount;

    @Column(name= "price_total")
    private double priceTotal;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    /**
     * case: for frameworks
     */
    public Order(){}

    /**
     * case: creation of new Order without order items.
     * ItemsAmount: 0, PriceAmount: 0.0
     */
    public Order(int customerId, PaymentStatus paymentStatus, OrderStatus orderStatus) {
        this.customerId = customerId;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
    }

    /**
     * case: creation of new Order with order items.
     * ItemsAmount and PriceAmount will be calculated.
     */
    public Order(int customerId, PaymentStatus paymentStatus, OrderStatus orderStatus, List<OrderItem> items) {
        this(customerId, paymentStatus, orderStatus);
        addOrderItems(items);
    }

    public void addOrderItems(OrderItem item){
        item.setOrder(this);
        this.items.add(item);
        countItems();
    }

    public void addOrderItems(List<OrderItem> items){
        for (OrderItem item : items) {
            addOrderItems(item);
        }
    }

    public void removeOrderItems(OrderItem item){
        this.items.remove(item);
        countItems();
    }

    public void removeOrderItems(List<OrderItem> items){
        for (OrderItem item : items) {
            removeOrderItems(item);
        }
    }

    private void countItems(){
        int count = 0;
        double price = 0;
        for (OrderItem item : items) {
            count++;
            price += item.getPrice();
        }
        this.itemsCount = count;
        this.priceTotal = price;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsAmount) {
        this.itemsCount = itemsAmount;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceAmount) {
        this.priceTotal = priceAmount;
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
                itemsCount == order.itemsCount &&
                Double.compare(order.priceTotal, priceTotal) == 0 &&
                paymentStatus == order.paymentStatus &&
                orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), customerId, itemsCount, priceTotal, paymentStatus, orderStatus, creation, closing);
    }

    @Override
    public String toString() {
        return "Order{" +
                ", customerId=" + customerId +
                ", itemsAmount=" + itemsCount +
                ", priceAmount=" + priceTotal +
                ", paymentStatus=" + paymentStatus +
                ", orderStatus=" + orderStatus +
                ", creationTS=" + creation +
                ", closingTS=" + closing +
                '}';
    }
}
