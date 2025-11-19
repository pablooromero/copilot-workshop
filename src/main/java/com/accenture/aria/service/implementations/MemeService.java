package com.accenture.aria.service.implementations;

import com.accenture.aria.dto.MemeRequestDTO;
import com.accenture.aria.dto.MemeResponseDTO;
import com.accenture.aria.exception.ResourceNotFoundException;
import com.accenture.aria.model.Meme;
import com.accenture.aria.repository.MemeRepository;
import com.accenture.aria.service.IMemeService;
import com.accenture.aria.service.mapper.MemeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemeService implements IMemeService {
    
    private final MemeRepository memeRepository;
    
    public MemeService(MemeRepository memeRepository) {
        this.memeRepository = memeRepository;
    }
    
    /**
     * Crea y persiste un nuevo meme tech argentino.
     * 
     * @param meme meme a crear
     * @return meme creado con ID generado
     */
    @Override
    public Meme create(Meme meme) {
        log.info("Creating new meme for character: {}", meme.getCharacter());
        Meme saved = memeRepository.save(meme);
        log.debug("Meme created with id: {}", saved.getId());
        return saved;
    }
    
    /**
     * Crea un meme desde un DTO de request.
     * 
     * @param dto datos del meme desde API
     * @return meme creado
     */
    @Override
    public Meme createFromDTO(MemeRequestDTO dto) {
        Meme meme = MemeMapper.toEntity(dto);
        return create(meme);
    }
    
    /**
     * Busca un meme por ID.
     * 
     * @param id identificador del meme
     * @return meme encontrado
     * @throws ResourceNotFoundException si no existe
     */
    @Override
    public MemeResponseDTO findById(Long id) {
        Meme meme = memeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Meme not found with id: {}", id);
                    return new ResourceNotFoundException("Meme", "id", id);
                });
        return MemeMapper.toResponse(meme);
    }
    
    /**
     * Lista todos los memes disponibles.
     * 
     * @return lista completa de memes
     */
    @Override
    public List<MemeResponseDTO> findAll() {
        return memeRepository.findAll().stream()
                .map(MemeMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualiza un meme existente.
     * Solo modifica campos no-null del meme recibido.
     * 
     * @param id identificador del meme
     * @param meme datos a actualizar
     * @return meme actualizado
     * @throws ResourceNotFoundException si no existe
     */
    @Override
    public Meme update(Long id, Meme meme) {
        Meme existing = memeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Meme not found with id: {} for update", id);
                    return new ResourceNotFoundException("Meme", "id", id);
                });
        
        if (meme.getCharacter() != null) {
            existing.setCharacter(meme.getCharacter());
        }
        if (meme.getDescription() != null) {
            existing.setDescription(meme.getDescription());
        }
        
        return memeRepository.save(existing);
    }
    
    /**
     * Actualiza un meme desde un DTO de request.
     * 
     * @param id identificador del meme
     * @param dto nuevos datos desde API
     * @return meme actualizado
     */
    @Override
    public Meme updateFromDTO(Long id, MemeRequestDTO dto) {
        Meme meme = MemeMapper.toEntity(dto);
        return update(id, meme);
    }
    
    /**
     * Elimina un meme por ID.
     * 
     * @param id identificador del meme
     * @throws ResourceNotFoundException si no existe
     */
    @Override
    public void delete(Long id) {
        log.info("Deleting meme with id: {}", id);
        if (!memeRepository.existsById(id)) {
            log.error("Meme not found with id: {}", id);
            throw new ResourceNotFoundException("Meme", "id", id);
        }
        memeRepository.deleteById(id);
        log.debug("Meme deleted successfully with id: {}", id);
    }
}
