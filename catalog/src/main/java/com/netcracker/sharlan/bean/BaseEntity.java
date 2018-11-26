package com.netcracker.sharlan.bean;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    private long baseId;

    public long getBaseId() {
        return baseId;
    }

    public void setBaseId(long id) {
        this.baseId = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "baseId=" + baseId +
                '}';
    }
}
