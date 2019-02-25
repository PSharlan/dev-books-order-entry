package com.netcracker.sharlan.entities;

public enum Role {
    ADMIN(0), USER(1);

    private long id;

    Role(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Role getRoleById(int id){
        switch (id){
            case 0: return ADMIN;
            case 1: return USER;
            default: return null;

        }
    }
}
