package com.netcracker.sharlan.dto.inventory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class OrderDto {

    private Long id;
    private Long customerId;
    private int itemsCount;
    private double priceTotal;
    private String paymentStatus;
    private String orderStatus;
    private Timestamp creation;
    private Timestamp closing;
    private List<OrderItemDto> items;

    public OrderDto() {
    }

    public OrderDto(Long customerId, String paymentStatus, String orderStatus, Timestamp creation, List<OrderItemDto> items) {
        this.customerId = customerId;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.creation = creation;
        this.items = items;
    }

    public OrderDto(Long id, Long customerId, int itemsCount, double priceTotal, String paymentStatus, String orderStatus, Timestamp creation, Timestamp closing, List<OrderItemDto> items) {
        this(customerId, paymentStatus, orderStatus, creation, items);
        this.id = id;
        this.itemsCount = itemsCount;
        this.priceTotal = priceTotal;
        this.closing = closing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getCreation() {
        return creation;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getClosing() {
        return closing;
    }

    public void setClosing(Timestamp closing) {
        this.closing = closing;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDto)) return false;
        OrderDto orderDto = (OrderDto) o;
        return itemsCount == orderDto.itemsCount &&
                Double.compare(orderDto.priceTotal, priceTotal) == 0 &&
                Objects.equals(id, orderDto.id) &&
                Objects.equals(customerId, orderDto.customerId) &&
                Objects.equals(paymentStatus, orderDto.paymentStatus) &&
                Objects.equals(orderStatus, orderDto.orderStatus) &&
                Objects.equals(creation, orderDto.creation) &&
                Objects.equals(closing, orderDto.closing) &&
                Objects.equals(items, orderDto.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, itemsCount, priceTotal, paymentStatus, orderStatus, creation, closing, items);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", itemsCount=" + itemsCount +
                ", priceTotal=" + priceTotal +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", creation=" + creation +
                ", closing=" + closing +
                ", items=" + items +
                '}';
    }
}
