package com.accenture.aria.controller;

import com.accenture.aria.dto.LawRequestDTO;
import com.accenture.aria.dto.LawResponseDTO;
import com.accenture.aria.model.LawCategory;
import com.accenture.aria.service.ILawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/laws")
@Tag(name = "Laws", description = "CRUD de leyes y axiomas del mundo IT")
public class LawController {
    
    private final ILawService lawService;
    
    public LawController(ILawService lawService) {
        this.lawService = lawService;
    }
    
    /**
     * Lista todas las leyes y axiomas IT disponibles.
     * 
     * @return lista completa de leyes
     */
    @GetMapping
    @Operation(summary = "Listar todas las leyes")
    public ResponseEntity<List<LawResponseDTO>> findAll() {
        return ResponseEntity.ok(lawService.findAll());
    }
    
    /**
     * Busca una ley por su ID.
     * 
     * @param id identificador de la ley
     * @return ley encontrada
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener ley por ID")
    public ResponseEntity<LawResponseDTO> findById(
            @Parameter(description = "ID de la ley")
            @PathVariable Long id) {
        return ResponseEntity.ok(lawService.findById(id));
    }
    
    /**
     * Busca leyes por categoría específica.
     * 
     * @param category categoría (MURPHY, HOFSTADTER, DILBERT, DEVOPS, AXIOM)
     * @return lista de leyes de la categoría solicitada
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Buscar leyes por categoría")
    public ResponseEntity<List<LawResponseDTO>> findByCategory(
            @Parameter(description = "Categoría (MURPHY, HOFSTADTER, DILBERT, DEVOPS, AXIOM)")
            @PathVariable LawCategory category) {
        return ResponseEntity.ok(lawService.findByCategory(category));
    }
    
    /**
     * Crea una nueva ley o axioma IT.
     * 
     * @param dto datos de la ley a crear
     * @return ley creada con HTTP 201
     */
    @PostMapping
    @Operation(summary = "Crear nueva ley")
    public ResponseEntity<LawResponseDTO> create(
            @Valid @RequestBody LawRequestDTO dto) {
        log.info("POST /api/laws called to create law: {} in category: {}", dto.getName(), dto.getCategory());
        var created = lawService.createFromDTO(dto);
        log.debug("Law created with id: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LawResponseDTO.builder()
                        .id(created.getId())
                        .name(created.getName())
                        .description(created.getDescription())
                        .category(created.getCategory())
                        .build());
    }
    
    /**
     * Actualiza una ley existente.
     * 
     * @param id identificador de la ley
     * @param dto nuevos datos de la ley
     * @return ley actualizada
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ley existente")
    public ResponseEntity<LawResponseDTO> update(
            @Parameter(description = "ID de la ley")
            @PathVariable Long id,
            @Valid @RequestBody LawRequestDTO dto) {
        var updated = lawService.updateFromDTO(id, dto);
        return ResponseEntity.ok(LawResponseDTO.builder()
                .id(updated.getId())
                .name(updated.getName())
                .description(updated.getDescription())
                .category(updated.getCategory())
                .build());
    }
    
    /**
     * Elimina una ley por su ID.
     * 
     * @param id identificador de la ley a eliminar
     * @return HTTP 204 sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ley")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la ley")
            @PathVariable Long id) {
        log.info("DELETE /api/laws/{} called", id);
        lawService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
