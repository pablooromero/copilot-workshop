package com.accenture.aria.controller;

import com.accenture.aria.dto.ExcuseResponseDTO;
import com.accenture.aria.model.Role;
import com.accenture.aria.service.IExcuseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/excuses")
@Tag(name = "Excuses", description = "API para generar excusas tech con fragmentos, memes y leyes")
public class ExcuseController {
    
    private final IExcuseService excuseService;
    
    public ExcuseController(IExcuseService excuseService) {
        this.excuseService = excuseService;
    }
    
    /**
     * Genera una excusa técnica aleatoria combinando fragmentos del dominio.
     * Soporta seed para reproducibilidad y filtro por rol opcional.
     * 
     * @param seed semilla para generar siempre la misma excusa (opcional)
     * @param role filtrar fragmentos por rol específico (opcional)
     * @return excusa generada con sus 4 fragmentos
     */
    @GetMapping("/random")
    @Operation(summary = "Generar excusa aleatoria", 
               description = "Genera una excusa tech aleatoria combinando fragmentos. Soporta seed opcional para reproducibilidad.")
    public ResponseEntity<ExcuseResponseDTO> random(
            @Parameter(description = "Seed para reproducibilidad (opcional)")
            @RequestParam(required = false) Long seed,
            @Parameter(description = "Filtrar fragmentos por rol (opcional)")
            @RequestParam(required = false) Role role) {
        log.info("GET /api/excuses/random called with seed: {}, role: {}", seed, role);
        return ResponseEntity.ok(excuseService.generateExcuse(seed, role));
    }
    
    /**
     * Genera la excusa del día usando la fecha como seed.
     * Devuelve la misma excusa durante 24 horas para todos los usuarios.
     * 
     * @return excusa del día
     */
    @GetMapping("/daily")
    @Operation(summary = "Excusa del día", 
               description = "Genera la excusa del día usando la fecha actual como seed. La misma excusa se devuelve durante 24 horas.")
    public ResponseEntity<ExcuseResponseDTO> daily() {
        log.info("GET /api/excuses/daily called");
        Long seed = LocalDate.now().toEpochDay();
        return ResponseEntity.ok(excuseService.generateExcuse(seed, null));
    }
    
    /**
     * Genera una excusa filtrada por rol específico.
     * Solo usa fragmentos del rol solicitado o genéricos (ALL).
     * 
     * @param role rol para filtrar fragmentos (DEV, QA, DEVOPS, PM, SRE)
     * @param seed semilla para reproducibilidad (opcional)
     * @return excusa específica del rol
     */
    @GetMapping("/role/{role}")
    @Operation(summary = "Excusa por rol", 
               description = "Genera excusa filtrada por rol específico (DEV, QA, DEVOPS, PM, SRE)")
    public ResponseEntity<ExcuseResponseDTO> byRole(
            @Parameter(description = "Rol para filtrar fragmentos")
            @PathVariable Role role,
            @Parameter(description = "Seed para reproducibilidad (opcional)")
            @RequestParam(required = false) Long seed) {
        log.info("GET /api/excuses/role/{} called with seed: {}", role, seed);
        return ResponseEntity.ok(excuseService.generateExcuse(seed, role));
    }
    
    /**
     * Genera una excusa con un meme tech argentino aleatorio incluido.
     * Combina fragmentos de excusa con un meme de la base de datos.
     * 
     * @param seed semilla para reproducibilidad (opcional)
     * @param role filtrar fragmentos por rol (opcional)
     * @return excusa con meme incluido
     */
    @GetMapping("/meme")
    @Operation(summary = "Excusa + Meme", 
               description = "Genera excusa con meme tech argentino incluido")
    public ResponseEntity<ExcuseResponseDTO> withMeme(
            @Parameter(description = "Seed para reproducibilidad (opcional)")
            @RequestParam(required = false) Long seed,
            @Parameter(description = "Filtrar fragmentos por rol (opcional)")
            @RequestParam(required = false) Role role) {
        log.info("GET /api/excuses/meme called with seed: {}, role: {}", seed, role);
        return ResponseEntity.ok(excuseService.generateExcuseWithMeme(seed, role));
    }
    
    /**
     * Genera una excusa con una ley o axioma IT aleatorio incluido.
     * Puede incluir leyes de Murphy, Hofstadter, Dilbert, DevOps o axiomas generales.
     * 
     * @param seed semilla para reproducibilidad (opcional)
     * @param role filtrar fragmentos por rol (opcional)
     * @return excusa con ley incluida
     */
    @GetMapping("/law")
    @Operation(summary = "Excusa + Ley", 
               description = "Genera excusa con ley/axioma del mundo IT incluida (Murphy, Hofstadter, Dilbert, DevOps, Axiomas)")
    public ResponseEntity<ExcuseResponseDTO> withLaw(
            @Parameter(description = "Seed para reproducibilidad (opcional)")
            @RequestParam(required = false) Long seed,
            @Parameter(description = "Filtrar fragmentos por rol (opcional)")
            @RequestParam(required = false) Role role) {
        log.info("GET /api/excuses/law called with seed: {}, role: {}", seed, role);
        return ResponseEntity.ok(excuseService.generateExcuseWithLaw(seed, role));
    }
    
    /**
     * Genera una excusa completa en modo ULTRA SHARK.
     * Incluye fragmentos de excusa + meme + ley simultáneamente.
     * 
     * @param seed semilla para reproducibilidad (opcional)
     * @param role filtrar fragmentos por rol (opcional)
     * @return excusa ULTRA con meme y ley
     */
    @GetMapping("/ultra")
    @Operation(summary = "Excusa ULTRA SHARK 🦈", 
               description = "Genera excusa completa con meme + ley incluidos (modo ULTRA SHARK)")
    public ResponseEntity<ExcuseResponseDTO> ultra(
            @Parameter(description = "Seed para reproducibilidad (opcional)")
            @RequestParam(required = false) Long seed,
            @Parameter(description = "Filtrar fragmentos por rol (opcional)")
            @RequestParam(required = false) Role role) {
        log.info("GET /api/excuses/ultra called with seed: {}, role: {} (ULTRA SHARK mode)", seed, role);
        return ResponseEntity.ok(excuseService.generateExcuseUltra(seed, role));
    }
    
    // ========== ENDPOINTS AI (Google Gemini) ==========
    
    /**
     * Genera una excusa con Google Gemini AI.
     * Permite configurar rol, contexto, nivel de creatividad y elementos adicionales.
     * 
     * @param request configuración de la excusa (rol, contexto, creatividad, meme, ley)
     * @return excusa generada por IA
     */
    @PostMapping("/ai")
    @Operation(summary = "Excusa generada por IA 🤖", 
               description = "Genera excusa tech creativa usando Google Gemini AI con control total sobre creatividad y elementos")
    public ResponseEntity<ExcuseResponseDTO> generateWithAI(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Parámetros de generación AI (role, context, creativity, includeMeme, includeLaw)")
            @RequestBody @jakarta.validation.Valid com.accenture.aria.dto.ExcuseAIRequestDTO request) {
        log.info("POST /api/excuses/ai called with request: {}", request);
        return ResponseEntity.ok(excuseService.generateExcuseWithAI(request));
    }
    
    /**
     * Genera una excusa rápida con IA usando creatividad media.
     * Endpoint conveniente sin necesidad de configurar creatividad.
     * 
     * @param role rol del usuario (por defecto DEV)
     * @return excusa AI con creatividad MEDIUM
     */
    @GetMapping("/ai/random")
    @Operation(summary = "Excusa AI aleatoria 🤖", 
               description = "Genera excusa AI con creatividad media para el rol especificado (shortcut conveniente)")
    public ResponseEntity<ExcuseResponseDTO> randomAI(
            @Parameter(description = "Rol del usuario (DEV, QA, DEVOPS, PM, SRE, ALL)")
            @RequestParam(defaultValue = "DEV") Role role) {
        log.info("GET /api/excuses/ai/random called with role: {}", role);
        
        com.accenture.aria.dto.ExcuseAIRequestDTO request = com.accenture.aria.dto.ExcuseAIRequestDTO.builder()
                .role(role)
                .creativity(com.accenture.aria.dto.ExcuseAIRequestDTO.CreativityLevel.MEDIUM)
                .includeMeme(false)
                .includeLaw(false)
                .build();
        
        return ResponseEntity.ok(excuseService.generateExcuseWithAI(request));
    }
    
    /**
     * Genera una excusa ultra creativa con IA en modo SHARK.
     * Usa máxima creatividad (HIGH) e incluye meme y ley automáticamente.
     * 
     * @param role rol del usuario (por defecto DEV)
     * @param context contexto adicional opcional (ej: "servidor caído")
     * @return excusa ULTRA AI con máxima creatividad
     */
    @GetMapping("/ai/creative")
    @Operation(summary = "Excusa AI ULTRA CREATIVA 🚀🤖", 
               description = "Genera excusa AI con máxima creatividad + meme + ley (modo ULTRA AI SHARK)")
    public ResponseEntity<ExcuseResponseDTO> creativeAI(
            @Parameter(description = "Rol del usuario (DEV, QA, DEVOPS, PM, SRE, ALL)")
            @RequestParam(defaultValue = "DEV") Role role,
            @Parameter(description = "Contexto adicional (opcional, ej: 'servidor caído en producción')")
            @RequestParam(required = false) String context) {
        log.info("GET /api/excuses/ai/creative called with role: {}, context: {} (ULTRA AI SHARK mode)", role, context);
        
        com.accenture.aria.dto.ExcuseAIRequestDTO request = com.accenture.aria.dto.ExcuseAIRequestDTO.builder()
                .role(role)
                .context(context)
                .creativity(com.accenture.aria.dto.ExcuseAIRequestDTO.CreativityLevel.HIGH)
                .includeMeme(true)
                .includeLaw(true)
                .build();
        
        return ResponseEntity.ok(excuseService.generateExcuseWithAI(request));
    }
}
