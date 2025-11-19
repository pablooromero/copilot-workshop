package com.accenture.aria.service.implementations;

import com.accenture.aria.dto.LawRequestDTO;
import com.accenture.aria.dto.LawResponseDTO;
import com.accenture.aria.exception.ResourceNotFoundException;
import com.accenture.aria.model.Law;
import com.accenture.aria.model.LawCategory;
import com.accenture.aria.repository.LawRepository;
import com.accenture.aria.service.ILawService;
import com.accenture.aria.service.mapper.LawMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LawService implements ILawService {
    
    private final LawRepository lawRepository;
    
    public LawService(LawRepository lawRepository) {
        this.lawRepository = lawRepository;
    }
    
    /**
     * Crea y persiste una nueva ley o axioma IT.
     * 
     * @param law ley a crear
     * @return ley creada con ID generado
     */
    @Override
    public Law create(Law law) {
        log.info("Creating new law: {} in category: {}", law.getName(), law.getCategory());
        Law saved = lawRepository.save(law);
        log.debug("Law created with id: {}", saved.getId());
        return saved;
    }
    
    /**
     * Crea una ley desde un DTO de request.
     * 
     * @param dto datos de la ley desde API
     * @return ley creada
     */
    @Override
    public Law createFromDTO(LawRequestDTO dto) {
        Law law = LawMapper.toEntity(dto);
        return create(law);
    }
    
    /**
     * Busca una ley por ID.
     * 
     * @param id identificador de la ley
     * @return ley encontrada
     * @throws ResourceNotFoundException si no existe
     */
    @Override
    public LawResponseDTO findById(Long id) {
        Law law = lawRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Law not found with id: {}", id);
                    return new ResourceNotFoundException("Law", "id", id);
                });
        return LawMapper.toResponse(law);
    }
    
    /**
     * Lista todas las leyes disponibles.
     * 
     * @return lista completa de leyes
     */
    @Override
    public List<LawResponseDTO> findAll() {
        return lawRepository.findAll().stream()
                .map(LawMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca leyes por categoría.
     * 
     * @param category categoría para filtrar
     * @return lista de leyes de la categoría
     */
    @Override
    public List<LawResponseDTO> findByCategory(LawCategory category) {
        return lawRepository.findByCategory(category).stream()
                .map(LawMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualiza una ley existente.
     * Solo modifica campos no-null de la ley recibida.
     * 
     * @param id identificador de la ley
     * @param law datos a actualizar
     * @return ley actualizada
     * @throws ResourceNotFoundException si no existe
     */
    @Override
    public Law update(Long id, Law law) {
        Law existing = lawRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Law not found with id: {} for update", id);
                    return new ResourceNotFoundException("Law", "id", id);
                });
        
        if (law.getName() != null) {
            existing.setName(law.getName());
        }
        if (law.getDescription() != null) {
            existing.setDescription(law.getDescription());
        }
        if (law.getCategory() != null) {
            existing.setCategory(law.getCategory());
        }
        
        return lawRepository.save(existing);
    }
    
    /**
     * Actualiza una ley desde un DTO de request.
     * 
     * @param id identificador de la ley
     * @param dto nuevos datos desde API
     * @return ley actualizada
     */
    @Override
    public Law updateFromDTO(Long id, LawRequestDTO dto) {
        Law law = LawMapper.toEntity(dto);
        return update(id, law);
    }
    
    /**
     * Elimina una ley por ID.
     * 
     * @param id identificador de la ley
     * @throws ResourceNotFoundException si no existe
     */
    @Override
    public void delete(Long id) {
        log.info("Deleting law with id: {}", id);
        if (!lawRepository.existsById(id)) {
            log.error("Law not found with id: {}", id);
            throw new ResourceNotFoundException("Law", "id", id);
        }
        lawRepository.deleteById(id);
        log.debug("Law deleted successfully with id: {}", id);
    }
}
