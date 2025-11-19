package com.accenture.aria.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción lanzada cuando hay un error de validación de datos de negocio.
 * Retorna HTTP 400 Bad Request.
 */
public class ValidationException extends BusinessException {
    
    private static final String DEFAULT_ERROR_CODE = "VALIDATION_ERROR";
    
    /**
     * Constructor con mensaje personalizado.
     */
    public ValidationException(String message) {
        super(message, DEFAULT_ERROR_CODE, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Constructor con mensaje y código de error personalizado.
     */
    public ValidationException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Constructor con causa raíz.
     */
    public ValidationException(String message, Throwable cause) {
        super(message, DEFAULT_ERROR_CODE, HttpStatus.BAD_REQUEST, cause);
    }
}
