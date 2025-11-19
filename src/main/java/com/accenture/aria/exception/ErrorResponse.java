package com.accenture.aria.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO estandarizado para respuestas de error HTTP.
 * Proporciona estructura consistente para todos los errores de la API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**
     * Código de estado HTTP (404, 400, 500, etc.)
     */
    private int status;
    
    /**
     * Nombre del error HTTP (Not Found, Bad Request, etc.)
     */
    private String error;
    
    /**
     * Mensaje descriptivo del error
     */
    private String message;
    
    /**
     * Código de error interno de la aplicación (FRAGMENT_NOT_FOUND, VALIDATION_ERROR, etc.)
     */
    private String errorCode;
    
    /**
     * Timestamp del error
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    /**
     * Path del endpoint que generó el error
     */
    private String path;
    
    /**
     * Detalles adicionales (opcional, para validaciones)
     */
    private List<String> details;
}
