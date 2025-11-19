package com.accenture.aria.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción lanzada cuando un recurso solicitado no existe en la base de datos.
 * Retorna HTTP 404 Not Found.
 */
public class ResourceNotFoundException extends BusinessException {
    
    private static final String DEFAULT_ERROR_CODE = "RESOURCE_NOT_FOUND";
    
    /**
     * Constructor con tipo de recurso y ID.
     * Genera mensaje automático: "{resourceType} not found with {fieldName}: {fieldValue}"
     */
    public ResourceNotFoundException(String resourceType, String fieldName, Object fieldValue) {
        super(
            String.format("%s not found with %s: %s", resourceType, fieldName, fieldValue),
            resourceType.toUpperCase() + "_NOT_FOUND",
            HttpStatus.NOT_FOUND
        );
    }
    
    /**
     * Constructor con mensaje personalizado.
     */
    public ResourceNotFoundException(String message) {
        super(message, DEFAULT_ERROR_CODE, HttpStatus.NOT_FOUND);
    }
    
    /**
     * Constructor con mensaje y código de error personalizado.
     */
    public ResourceNotFoundException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.NOT_FOUND);
    }
}
