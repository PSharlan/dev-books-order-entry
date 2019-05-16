package com.devbooks.sharlan.exception;

public class EntityNotFoundException extends RuntimeException {

    private Class notFoundedClass;
    private Long entityId;

    public EntityNotFoundException(Class notFoundedClass, Long entityId){
        this.entityId = entityId;
        this.notFoundedClass = notFoundedClass;
    }

    public Class getNotFoundedClass() {
        return notFoundedClass;
    }

    public void setNotFoundedClass(Class notFoundedClass) {
        this.notFoundedClass = notFoundedClass;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
