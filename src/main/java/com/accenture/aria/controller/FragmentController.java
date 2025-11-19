package com.accenture.aria.controller;

import com.accenture.aria.dto.FragmentRequestDTO;
import com.accenture.aria.dto.FragmentResponseDTO;
import com.accenture.aria.model.FragmentType;
import com.accenture.aria.model.Role;
import com.accenture.aria.service.IFragmentService;
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
@RequestMapping("/api/fragments")
@Tag(name = "Fragments", description = "CRUD de fragmentos de excusas (contexto, causa, consecuencia, recomendación)")
public class FragmentController {
    
    private final IFragmentService fragmentService;
    
    public FragmentController(IFragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }
    
    /**
     * Lista todos los fragmentos de excusas disponibles.
     * 
     * @return lista completa de fragmentos
     */
    @GetMapping
    @Operation(summary = "Listar todos los fragmentos")
    public ResponseEntity<List<FragmentResponseDTO>> findAll() {
        return ResponseEntity.ok(fragmentService.findAll());
    }
    
    /**
     * Busca un fragmento por su ID.
     * 
     * @param id identificador del fragmento
     * @return fragmento encontrado
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener fragmento por ID")
    public ResponseEntity<FragmentResponseDTO> findById(
            @Parameter(description = "ID del fragmento")
            @PathVariable Long id) {
        return ResponseEntity.ok(fragmentService.findById(id));
    }
    
    /**
     * Busca fragmentos por tipo específico.
     * 
     * @param type tipo de fragmento (CONTEXTO, CAUSA, CONSECUENCIA, RECOMENDACION)
     * @return lista de fragmentos del tipo solicitado
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "Buscar fragmentos por tipo")
    public ResponseEntity<List<FragmentResponseDTO>> findByType(
            @Parameter(description = "Tipo de fragmento (CONTEXTO, CAUSA, CONSECUENCIA, RECOMENDACION)")
            @PathVariable FragmentType type) {
        return ResponseEntity.ok(fragmentService.findByType(type));
    }
    
    /**
     * Busca fragmentos por rol específico.
     * 
     * @param role rol para filtrar (DEV, QA, DEVOPS, PM, SRE, ALL)
     * @return lista de fragmentos del rol solicitado
     */
    @GetMapping("/role/{role}")
    @Operation(summary = "Buscar fragmentos por rol")
    public ResponseEntity<List<FragmentResponseDTO>> findByRole(
            @Parameter(description = "Rol (DEV, QA, DEVOPS, PM, SRE, ALL)")
            @PathVariable Role role) {
        return ResponseEntity.ok(fragmentService.findByRole(role));
    }
    
    /**
     * Crea un nuevo fragmento de excusa.
     * 
     * @param dto datos del fragmento a crear
     * @return fragmento creado con HTTP 201
     */
    @PostMapping
    @Operation(summary = "Crear nuevo fragmento")
    public ResponseEntity<FragmentResponseDTO> create(
            @Valid @RequestBody FragmentRequestDTO dto) {
        log.info("POST /api/fragments called to create fragment of type: {}", dto.getType());
        var created = fragmentService.createFromDTO(dto);
        log.debug("Fragment created with id: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FragmentResponseDTO.builder()
                        .id(created.getId())
                        .type(created.getType())
                        .text(created.getText())
                        .role(created.getRole())
                        .build());
    }
    
    /**
     * Actualiza un fragmento existente.
     * 
     * @param id identificador del fragmento
     * @param dto nuevos datos del fragmento
     * @return fragmento actualizado
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar fragmento existente")
    public ResponseEntity<FragmentResponseDTO> update(
            @Parameter(description = "ID del fragmento")
            @PathVariable Long id,
            @Valid @RequestBody FragmentRequestDTO dto) {
        var updated = fragmentService.updateFromDTO(id, dto);
        return ResponseEntity.ok(FragmentResponseDTO.builder()
                .id(updated.getId())
                .type(updated.getType())
                .text(updated.getText())
                .role(updated.getRole())
                .build());
    }
    
    /**
     * Elimina un fragmento por su ID.
     * 
     * @param id identificador del fragmento a eliminar
     * @return HTTP 204 sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar fragmento")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del fragmento")
            @PathVariable Long id) {
        log.info("DELETE /api/fragments/{} called", id);
        fragmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
