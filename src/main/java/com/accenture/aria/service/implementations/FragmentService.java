package com.accenture.aria.service.implementations;

import com.accenture.aria.dto.FragmentRequestDTO;
import com.accenture.aria.dto.FragmentResponseDTO;
import com.accenture.aria.exception.ResourceNotFoundException;
import com.accenture.aria.model.Fragment;
import com.accenture.aria.model.FragmentType;
import com.accenture.aria.model.Role;
import com.accenture.aria.repository.FragmentRepository;
import com.accenture.aria.service.IFragmentService;
import com.accenture.aria.service.mapper.FragmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FragmentService implements IFragmentService {
    
    private final FragmentRepository fragmentRepository;
    
    public FragmentService(FragmentRepository fragmentRepository) {
        this.fragmentRepository = fragmentRepository;
    }
    
    /**
     * Crea y persiste un nuevo fragmento de excusa.
     * 
     * @param fragment fragmento a crear
     * @return fragmento creado con ID generado
     */
    @Override
    public Fragment create(Fragment fragment) {
        log.info("Creating new fragment of type: {} for role: {}", fragment.getType(), fragment.getRole());
        Fragment saved = fragmentRepository.save(fragment);
        log.debug("Fragment created with id: {}", saved.getId());
        return saved;
    }
    
    /**
     * Crea un fragmento desde un DTO de request.
     * 
     * @param dto datos del fragmento desde API
     * @return fragmento creado
     */
    @Override
    public Fragment createFromDTO(FragmentRequestDTO dto) {
        Fragment fragment = FragmentMapper.toEntity(dto);
        return create(fragment);
    }
    
    /**
     * Busca un fragmento por ID.
     * 
     * @param id identificador del fragmento
     * @return fragmento encontrado
     * @throws ResourceNotFoundException si no existe
     */
    @Override
    public FragmentResponseDTO findById(Long id) {
        log.debug("Finding fragment by id: {}", id);
        Fragment fragment = fragmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fragment not found with id: {}", id);
                    return new ResourceNotFoundException("Fragment", "id", id);
                });
        return FragmentMapper.toResponse(fragment);
    }
    
    /**
     * Lista todos los fragmentos disponibles.
     * 
     * @return lista completa de fragmentos
     */
    @Override
    public List<FragmentResponseDTO> findAll() {
        return fragmentRepository.findAll().stream()
                .map(FragmentMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca fragmentos por tipo.
     * 
     * @param type tipo de fragmento
     * @return lista de fragmentos del tipo
     */
    @Override
    public List<FragmentResponseDTO> findByType(FragmentType type) {
        return fragmentRepository.findByType(type).stream()
                .map(FragmentMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca fragmentos por rol.
     * 
     * @param role rol para filtrar
     * @return lista de fragmentos del rol
     */
    @Override
    public List<FragmentResponseDTO> findByRole(Role role) {
        return fragmentRepository.findByRole(role).stream()
                .map(FragmentMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualiza un fragmento existente.
     * Solo modifica campos no-null del fragmento recibido.
     * 
     * @param id identificador del fragmento
     * @param fragment datos a actualizar
     * @return fragmento actualizado
     * @throws ResourceNotFoundException si no existe
     */
    @Override
    public Fragment update(Long id, Fragment fragment) {
        Fragment existing = fragmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fragment not found with id: {} for update", id);
                    return new ResourceNotFoundException("Fragment", "id", id);
                });
        
        if (fragment.getType() != null) {
            existing.setType(fragment.getType());
        }
        if (fragment.getText() != null) {
            existing.setText(fragment.getText());
        }
        if (fragment.getRole() != null) {
            existing.setRole(fragment.getRole());
        }
        
        return fragmentRepository.save(existing);
    }
    
    /**
     * Actualiza un fragmento desde un DTO de request.
     * 
     * @param id identificador del fragmento
     * @param dto nuevos datos desde API
     * @return fragmento actualizado
     */
    @Override
    public Fragment updateFromDTO(Long id, FragmentRequestDTO dto) {
        Fragment fragment = FragmentMapper.toEntity(dto);
        return update(id, fragment);
    }
    
    /**
     * Elimina un fragmento por ID.
     * 
     * @param id identificador del fragmento
     * @throws ResourceNotFoundException si no existe
     */
    @Override
    public void delete(Long id) {
        log.info("Deleting fragment with id: {}", id);
        if (!fragmentRepository.existsById(id)) {
            log.error("Fragment not found with id: {}", id);
            throw new ResourceNotFoundException("Fragment", "id", id);
        }
        fragmentRepository.deleteById(id);
        log.debug("Fragment deleted successfully with id: {}", id);
    }
}
