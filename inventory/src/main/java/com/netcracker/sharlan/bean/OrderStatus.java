package com.netcracker.sharlan.bean;

public enum OrderStatus {
    NEW(0), PENDING(1), DELIVERY(2), CLOSED(3), CANCELED(10);

    private int id;

    OrderStatus(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
