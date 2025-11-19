package com.accenture.aria.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Excepción base para todas las excepciones de negocio de la aplicación.
 * Proporciona estructura común para manejo de errores con código, timestamp y HTTP status.
 */
@Getter
public abstract class BusinessException extends RuntimeException {
    
    private final String errorCode;
    private final LocalDateTime timestamp;
    private final HttpStatus httpStatus;
    
    protected BusinessException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }
    
    protected BusinessException(String message, String errorCode, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }
}
