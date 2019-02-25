package com.netcracker.sharlan.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private Class notFoundedClass;
    private long entityId;
    private String searchParam;

    public EntityNotFoundException(Class notFoundedClass, long entityId){
        this.entityId = entityId;
        this.notFoundedClass = notFoundedClass;
    }

    public EntityNotFoundException(Class notFoundedClass, String searchParam){
        this.searchParam = searchParam;
        this.notFoundedClass = notFoundedClass;
    }

    public Class getNotFoundedClass() {
        return notFoundedClass;
    }

    public void setNotFoundedClass(Class notFoundedClass) {
        this.notFoundedClass = notFoundedClass;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }
}
