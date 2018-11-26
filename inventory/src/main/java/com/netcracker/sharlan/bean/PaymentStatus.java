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

    public PaymentStatus getStatusById(int id){
        switch (id){
            case 0: return NONE;
            case 1: return BILLED;
            case 2: return PAID;
            case 10: return CANCELED;
            default: return null;
        }
    }
}