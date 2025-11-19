package com.accenture.aria.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para errores relacionados con Google Gemini AI API.
 * HTTP 503 Service Unavailable - permite fallback a generación tradicional.
 */
public class GeminiException extends BusinessException {

    public enum GeminiErrorCode {
        API_ERROR("Error al comunicarse con Gemini API"),
        QUOTA_EXCEEDED("Cuota de API excedida"),
        INVALID_API_KEY("API Key inválida o no configurada"),
        TIMEOUT("Timeout al esperar respuesta de Gemini API"),
        INVALID_RESPONSE("Respuesta inválida de Gemini API");

        private final String description;

        GeminiErrorCode(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public GeminiException(GeminiErrorCode errorCode, String message) {
        super(errorCode.name(), message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public GeminiException(GeminiErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.name(), message, HttpStatus.SERVICE_UNAVAILABLE, cause);
    }
}
