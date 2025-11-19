package com.accenture.aria.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción lanzada cuando hay un conflicto con el estado actual del recurso.
 * Por ejemplo: intentar crear un recurso que ya existe.
 * Retorna HTTP 409 Conflict.
 */
public class DataConflictException extends BusinessException {
    
    private static final String DEFAULT_ERROR_CODE = "DATA_CONFLICT";
    
    /**
     * Constructor con mensaje personalizado.
     */
    public DataConflictException(String message) {
        super(message, DEFAULT_ERROR_CODE, HttpStatus.CONFLICT);
    }
    
    /**
     * Constructor con mensaje y código de error personalizado.
     */
    public DataConflictException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.CONFLICT);
    }
}
