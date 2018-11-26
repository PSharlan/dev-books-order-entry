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

    public OrderStatus getStatusById(int id){
        switch (id){
            case 0: return NEW;
            case 1: return PENDING;
            case 2: return DELIVERY;
            case 3: return CLOSED;
            case 10: return CANCELED;
            default: return null;

        }
    }

}
