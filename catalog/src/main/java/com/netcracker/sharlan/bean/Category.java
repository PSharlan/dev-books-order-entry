package com.netcracker.sharlan.bean;

public class Category {

    private String name;

    public Category(String name){
        this.name = name;
    }

    public Category(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}