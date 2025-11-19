package com.accenture.aria.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuración de Google Gemini AI.
 * Inicializa el cliente HTTP para llamadas a la API REST de Gemini.
 */
@Slf4j
@Configuration
public class GeminiConfig {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.timeout:30}")
    private int timeoutSeconds;

    @Bean
    public OkHttpClient geminiHttpClient() {
        log.info("Initializing Gemini HTTP client with timeout: {}s", timeoutSeconds);
        
        if (apiKey == null || apiKey.isBlank() || apiKey.equals("your_api_key_here")) {
            log.warn("GEMINI_API_KEY not configured! AI features will fallback to traditional generation");
        }

        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(timeoutSeconds))
                .writeTimeout(Duration.ofSeconds(timeoutSeconds))
                .readTimeout(Duration.ofSeconds(timeoutSeconds))
                .build();
    }

    @Bean
    public String geminiApiKey() {
        return apiKey;
    }
}
