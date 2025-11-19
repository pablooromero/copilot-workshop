package com.accenture.aria.dto;

import com.accenture.aria.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO para generación de excusas con IA.
 * Permite personalizar el nivel de creatividad y elementos adicionales.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcuseAIRequestDTO {

    /**
     * Rol del usuario (DEV, QA, DEVOPS, PM, SRE, ALL).
     * Determina el estilo técnico de la excusa.
     */
    @NotNull(message = "Role is required")
    private Role role;

    /**
     * Contexto adicional opcional para personalizar la excusa.
     * Ejemplo: "servidor de producción caído", "deploy fallido", etc.
     */
    private String context;

    /**
     * Nivel de creatividad de la IA.
     * LOW (0.3) = predecible, MEDIUM (0.8) = balanceado, HIGH (1.2) = muy creativo.
     */
    @NotNull(message = "Creativity level is required")
    @Builder.Default
    private CreativityLevel creativity = CreativityLevel.MEDIUM;

    /**
     * Si debe incluir un meme argentino/tech.
     */
    @Builder.Default
    private Boolean includeMeme = false;

    /**
     * Si debe incluir una ley/axioma de Murphy, Hofstadter, Dilbert, etc.
     */
    @Builder.Default
    private Boolean includeLaw = false;

    /**
     * Nivel de creatividad para la temperatura de Gemini AI.
     */
    public enum CreativityLevel {
        LOW(0.3),
        MEDIUM(0.8),
        HIGH(1.2);

        private final double temperature;

        CreativityLevel(double temperature) {
            this.temperature = temperature;
        }

        public double getTemperature() {
            return temperature;
        }
    }
}
