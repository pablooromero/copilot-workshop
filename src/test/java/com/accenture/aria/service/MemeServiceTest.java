package com.accenture.aria.service;

import com.accenture.aria.dto.MemeRequestDTO;
import com.accenture.aria.dto.MemeResponseDTO;
import com.accenture.aria.exception.ResourceNotFoundException;
import com.accenture.aria.model.Meme;
import com.accenture.aria.repository.MemeRepository;
import com.accenture.aria.service.implementations.MemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MemeServiceTest {
    
    @Mock
    private MemeRepository memeRepository;
    
    private IMemeService memeService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memeService = new MemeService(memeRepository);
    }
    
    @Test
    void testCreate_ShouldSaveMeme() {
        // Given
        Meme meme = Meme.builder()
                .character("Tano Pasman")
                .description("¡¿CÓMO QUE FALLÓ EL PIPELINE?!")
                .build();
        
        Meme savedMeme = Meme.builder()
                .id(1L)
                .character("Tano Pasman")
                .description("¡¿CÓMO QUE FALLÓ EL PIPELINE?!")
                .build();
        
        when(memeRepository.save(any(Meme.class))).thenReturn(savedMeme);
        
        // When
        Meme result = memeService.create(meme);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Tano Pasman", result.getCharacter());
        assertEquals("¡¿CÓMO QUE FALLÓ EL PIPELINE?!", result.getDescription());
        verify(memeRepository, times(1)).save(meme);
    }
    
    @Test
    void testCreateFromDTO_ShouldConvertAndSave() {
        // Given
        MemeRequestDTO dto = MemeRequestDTO.builder()
                .character("Meme Argento")
                .description("Nada como un 'está en UAT' para saber que nadie lo probó")
                .build();
        
        Meme savedMeme = Meme.builder()
                .id(2L)
                .character("Meme Argento")
                .description("Nada como un 'está en UAT' para saber que nadie lo probó")
                .build();
        
        when(memeRepository.save(any(Meme.class))).thenReturn(savedMeme);
        
        // When
        Meme result = memeService.createFromDTO(dto);
        
        // Then
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Meme Argento", result.getCharacter());
        verify(memeRepository, times(1)).save(any(Meme.class));
    }
    
    @Test
    void testFindById_WhenExists_ShouldReturnMeme() {
        // Given
        Long memeId = 1L;
        Meme meme = Meme.builder()
                .id(memeId)
                .character("Dev Meme")
                .description("Funciona en mi máquina")
                .build();
        
        when(memeRepository.findById(memeId)).thenReturn(Optional.of(meme));
        
        // When
        MemeResponseDTO result = memeService.findById(memeId);
        
        // Then
        assertNotNull(result);
        assertEquals(memeId, result.getId());
        assertEquals("Dev Meme", result.getCharacter());
        assertEquals("Funciona en mi máquina", result.getDescription());
        verify(memeRepository, times(1)).findById(memeId);
    }
    
    @Test
    void testFindById_WhenNotExists_ShouldThrowException() {
        // Given
        Long memeId = 999L;
        when(memeRepository.findById(memeId)).thenReturn(Optional.empty());
        
        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            memeService.findById(memeId);
        });
        
        assertTrue(exception.getMessage().contains("Meme not found with id: 999"));
        verify(memeRepository, times(1)).findById(memeId);
    }
    
    @Test
    void testFindAll_ShouldReturnAllMemes() {
        // Given
        List<Meme> memes = Arrays.asList(
            Meme.builder().id(1L).character("Tano Pasman").description("Descripción 1").build(),
            Meme.builder().id(2L).character("Meme Argento").description("Descripción 2").build(),
            Meme.builder().id(3L).character("Dev Meme").description("Descripción 3").build()
        );
        
        when(memeRepository.findAll()).thenReturn(memes);
        
        // When
        List<MemeResponseDTO> result = memeService.findAll();
        
        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Tano Pasman", result.get(0).getCharacter());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Meme Argento", result.get(1).getCharacter());
        verify(memeRepository, times(1)).findAll();
    }
    
    @Test
    void testUpdate_ShouldModifyExistingMeme() {
        // Given
        Long memeId = 1L;
        Meme existing = Meme.builder()
                .id(memeId)
                .character("Personaje original")
                .description("Descripción original")
                .build();
        
        Meme updates = Meme.builder()
                .character("Personaje actualizado")
                .description("Descripción actualizada")
                .build();
        
        Meme updated = Meme.builder()
                .id(memeId)
                .character("Personaje actualizado")
                .description("Descripción actualizada")
                .build();
        
        when(memeRepository.findById(memeId)).thenReturn(Optional.of(existing));
        when(memeRepository.save(any(Meme.class))).thenReturn(updated);
        
        // When
        Meme result = memeService.update(memeId, updates);
        
        // Then
        assertNotNull(result);
        assertEquals("Personaje actualizado", result.getCharacter());
        assertEquals("Descripción actualizada", result.getDescription());
        verify(memeRepository, times(1)).findById(memeId);
        verify(memeRepository, times(1)).save(any(Meme.class));
    }
    
    @Test
    void testUpdate_PartialFields_ShouldUpdateOnlyProvided() {
        // Given
        Long memeId = 1L;
        Meme existing = Meme.builder()
                .id(memeId)
                .character("Personaje original")
                .description("Descripción original")
                .build();
        
        // Solo actualizar descripción
        Meme updates = Meme.builder()
                .description("Solo nueva descripción")
                .build();
        
        when(memeRepository.findById(memeId)).thenReturn(Optional.of(existing));
        when(memeRepository.save(any(Meme.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // When
        Meme result = memeService.update(memeId, updates);
        
        // Then
        assertNotNull(result);
        assertEquals("Personaje original", result.getCharacter(), "Character should remain unchanged");
        assertEquals("Solo nueva descripción", result.getDescription(), "Description should be updated");
        verify(memeRepository, times(1)).save(any(Meme.class));
    }
    
    @Test
    void testDelete_ShouldRemoveMeme() {
        // Given
        Long memeId = 1L;
        when(memeRepository.existsById(memeId)).thenReturn(true);
        doNothing().when(memeRepository).deleteById(memeId);
        
        // When
        memeService.delete(memeId);
        
        // Then
        verify(memeRepository, times(1)).existsById(memeId);
        verify(memeRepository, times(1)).deleteById(memeId);
    }
}
