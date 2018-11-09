package com.netcracker.sharlan.beans;

public enum Category {

    book(1), program(2), game(3);

    private int id;

    Category(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

}
