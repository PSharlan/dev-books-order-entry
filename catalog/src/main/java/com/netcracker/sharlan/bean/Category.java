package com.netcracker.sharlan.bean;

import javax.persistence.*;

@Entity
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @Column(name="name", nullable = false, unique = true)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
