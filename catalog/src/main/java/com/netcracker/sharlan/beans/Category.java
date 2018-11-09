package com.netcracker.sharlan.beans;

public enum Category {
    ; // Categories gonna be added

    private int id;

    Category(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

}
