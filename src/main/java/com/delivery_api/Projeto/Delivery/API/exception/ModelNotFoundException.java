package com.delivery_api.Projeto.Delivery.API.exception;

public class ModelNotFoundException extends BusinessException {
    private String entityName;
    private Object entityId;

    public ModelNotFoundException(String entityName, Object entityId) {
        super(String.format("%s com ID %s não foi encontrado(a)", entityName, entityId));
        this.entityName = entityName;
        this.entityId = entityId;
        this.setErrorCode("ENTITY_NOT_FOUND");
    }

    public ModelNotFoundException(String message) {
        super(message);
        this.setErrorCode("ENTITY_NOT_FOUND");
    }

    public String getEntityName() {
        return entityName;
    }

    public Object getEntityId() {
        return entityId;
    }
}