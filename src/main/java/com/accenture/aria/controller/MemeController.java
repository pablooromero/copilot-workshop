package com.accenture.aria.controller;

import com.accenture.aria.dto.MemeRequestDTO;
import com.accenture.aria.dto.MemeResponseDTO;
import com.accenture.aria.service.IMemeService;
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
@RequestMapping("/api/memes")
@Tag(name = "Memes", description = "CRUD de memes tech argentinos")
public class MemeController {
    
    private final IMemeService memeService;
    
    public MemeController(IMemeService memeService) {
        this.memeService = memeService;
    }
    
    /**
     * Lista todos los memes tech argentinos disponibles.
     * 
     * @return lista completa de memes
     */
    @GetMapping
    @Operation(summary = "Listar todos los memes")
    public ResponseEntity<List<MemeResponseDTO>> findAll() {
        return ResponseEntity.ok(memeService.findAll());
    }
    
    /**
     * Busca un meme por su ID.
     * 
     * @param id identificador del meme
     * @return meme encontrado
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener meme por ID")
    public ResponseEntity<MemeResponseDTO> findById(
            @Parameter(description = "ID del meme")
            @PathVariable Long id) {
        return ResponseEntity.ok(memeService.findById(id));
    }
    
    /**
     * Crea un nuevo meme tech argentino.
     * 
     * @param dto datos del meme a crear
     * @return meme creado con HTTP 201
     */
    @PostMapping
    @Operation(summary = "Crear nuevo meme")
    public ResponseEntity<MemeResponseDTO> create(
            @Valid @RequestBody MemeRequestDTO dto) {
        log.info("POST /api/memes called to create meme for character: {}", dto.getCharacter());
        var created = memeService.createFromDTO(dto);
        log.debug("Meme created with id: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MemeResponseDTO.builder()
                        .id(created.getId())
                        .character(created.getCharacter())
                        .description(created.getDescription())
                        .build());
    }
    
    /**
     * Actualiza un meme existente.
     * 
     * @param id identificador del meme
     * @param dto nuevos datos del meme
     * @return meme actualizado
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar meme existente")
    public ResponseEntity<MemeResponseDTO> update(
            @Parameter(description = "ID del meme")
            @PathVariable Long id,
            @Valid @RequestBody MemeRequestDTO dto) {
        var updated = memeService.updateFromDTO(id, dto);
        return ResponseEntity.ok(MemeResponseDTO.builder()
                .id(updated.getId())
                .character(updated.getCharacter())
                .description(updated.getDescription())
                .build());
    }
    
    /**
     * Elimina un meme por su ID.
     * 
     * @param id identificador del meme a eliminar
     * @return HTTP 204 sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar meme")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del meme")
            @PathVariable Long id) {
        log.info("DELETE /api/memes/{} called", id);
        memeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
