package com.netcracker.sharlan.beans;

public enum Tag {
    ; // Tags gonna be added

    private int id;

    Tag(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
