package com.netcracker.sharlan.bean;

public enum PaymentStatus {
    NONE(0), BILLED(1), PAID(2), CANCELED(10);

    private int id;

    PaymentStatus(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}