package com.netcracker.sharlan.dto;

public enum RoleDto {
    ADMIN(0), USER(1);

    private long id;

    RoleDto(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
