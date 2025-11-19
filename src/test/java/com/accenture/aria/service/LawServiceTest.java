package com.accenture.aria.service;

import com.accenture.aria.dto.LawRequestDTO;
import com.accenture.aria.dto.LawResponseDTO;
import com.accenture.aria.exception.ResourceNotFoundException;
import com.accenture.aria.model.Law;
import com.accenture.aria.model.LawCategory;
import com.accenture.aria.repository.LawRepository;
import com.accenture.aria.service.implementations.LawService;
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

class LawServiceTest {
    
    @Mock
    private LawRepository lawRepository;
    
    private ILawService lawService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lawService = new LawService(lawRepository);
    }
    
    @Test
    void testCreate_ShouldSaveLaw() {
        // Given
        Law law = Law.builder()
                .name("Ley de Murphy")
                .description("Si algo puede salir mal, saldrá mal en producción")
                .category(LawCategory.MURPHY)
                .build();
        
        Law savedLaw = Law.builder()
                .id(1L)
                .name("Ley de Murphy")
                .description("Si algo puede salir mal, saldrá mal en producción")
                .category(LawCategory.MURPHY)
                .build();
        
        when(lawRepository.save(any(Law.class))).thenReturn(savedLaw);
        
        // When
        Law result = lawService.create(law);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ley de Murphy", result.getName());
        assertEquals("Si algo puede salir mal, saldrá mal en producción", result.getDescription());
        assertEquals(LawCategory.MURPHY, result.getCategory());
        verify(lawRepository, times(1)).save(law);
    }
    
    @Test
    void testCreateFromDTO_ShouldConvertAndSave() {
        // Given
        LawRequestDTO dto = LawRequestDTO.builder()
                .name("Ley de Hofstadter")
                .description("Siempre toma más tiempo del que esperabas")
                .category(LawCategory.HOFSTADTER)
                .build();
        
        Law savedLaw = Law.builder()
                .id(2L)
                .name("Ley de Hofstadter")
                .description("Siempre toma más tiempo del que esperabas")
                .category(LawCategory.HOFSTADTER)
                .build();
        
        when(lawRepository.save(any(Law.class))).thenReturn(savedLaw);
        
        // When
        Law result = lawService.createFromDTO(dto);
        
        // Then
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Ley de Hofstadter", result.getName());
        assertEquals(LawCategory.HOFSTADTER, result.getCategory());
        verify(lawRepository, times(1)).save(any(Law.class));
    }
    
    @Test
    void testFindById_WhenExists_ShouldReturnLaw() {
        // Given
        Long lawId = 1L;
        Law law = Law.builder()
                .id(lawId)
                .name("Principio de Dilbert")
                .description("Los que menos saben son ascendidos primero")
                .category(LawCategory.DILBERT)
                .build();
        
        when(lawRepository.findById(lawId)).thenReturn(Optional.of(law));
        
        // When
        LawResponseDTO result = lawService.findById(lawId);
        
        // Then
        assertNotNull(result);
        assertEquals(lawId, result.getId());
        assertEquals("Principio de Dilbert", result.getName());
        assertEquals("Los que menos saben son ascendidos primero", result.getDescription());
        assertEquals(LawCategory.DILBERT, result.getCategory());
        verify(lawRepository, times(1)).findById(lawId);
    }
    
    @Test
    void testFindById_WhenNotExists_ShouldThrowException() {
        // Given
        Long lawId = 999L;
        when(lawRepository.findById(lawId)).thenReturn(Optional.empty());
        
        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            lawService.findById(lawId);
        });
        
        assertTrue(exception.getMessage().contains("Law not found with id: 999"));
        verify(lawRepository, times(1)).findById(lawId);
    }
    
    @Test
    void testFindAll_ShouldReturnAllLaws() {
        // Given
        List<Law> laws = Arrays.asList(
            Law.builder().id(1L).name("Murphy").description("Ley 1").category(LawCategory.MURPHY).build(),
            Law.builder().id(2L).name("Hofstadter").description("Ley 2").category(LawCategory.HOFSTADTER).build(),
            Law.builder().id(3L).name("Dilbert").description("Ley 3").category(LawCategory.DILBERT).build()
        );
        
        when(lawRepository.findAll()).thenReturn(laws);
        
        // When
        List<LawResponseDTO> result = lawService.findAll();
        
        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Murphy", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Hofstadter", result.get(1).getName());
        verify(lawRepository, times(1)).findAll();
    }
    
    @Test
    void testUpdate_ShouldModifyExistingLaw() {
        // Given
        Long lawId = 1L;
        Law existing = Law.builder()
                .id(lawId)
                .name("Nombre original")
                .description("Descripción original")
                .category(LawCategory.MURPHY)
                .build();
        
        Law updates = Law.builder()
                .name("Nombre actualizado")
                .description("Descripción actualizada")
                .category(LawCategory.HOFSTADTER)
                .build();
        
        Law updated = Law.builder()
                .id(lawId)
                .name("Nombre actualizado")
                .description("Descripción actualizada")
                .category(LawCategory.HOFSTADTER)
                .build();
        
        when(lawRepository.findById(lawId)).thenReturn(Optional.of(existing));
        when(lawRepository.save(any(Law.class))).thenReturn(updated);
        
        // When
        Law result = lawService.update(lawId, updates);
        
        // Then
        assertNotNull(result);
        assertEquals("Nombre actualizado", result.getName());
        assertEquals("Descripción actualizada", result.getDescription());
        assertEquals(LawCategory.HOFSTADTER, result.getCategory());
        verify(lawRepository, times(1)).findById(lawId);
        verify(lawRepository, times(1)).save(any(Law.class));
    }
    
    @Test
    void testDelete_ShouldRemoveLaw() {
        // Given
        Long lawId = 1L;
        when(lawRepository.existsById(lawId)).thenReturn(true);
        doNothing().when(lawRepository).deleteById(lawId);
        
        // When
        lawService.delete(lawId);
        
        // Then
        verify(lawRepository, times(1)).existsById(lawId);
        verify(lawRepository, times(1)).deleteById(lawId);
    }
    
    @Test
    void testGetByCategory_ShouldReturnFilteredLaws() {
        // Given
        LawCategory category = LawCategory.DEVOPS;
        List<Law> laws = Arrays.asList(
            Law.builder().id(1L).name("Principio DevOps 1").description("Desc 1").category(category).build(),
            Law.builder().id(2L).name("Principio DevOps 2").description("Desc 2").category(category).build()
        );
        
        when(lawRepository.findByCategory(category)).thenReturn(laws);
        
        // When
        List<LawResponseDTO> result = lawService.findByCategory(category);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(l -> l.getCategory() == LawCategory.DEVOPS));
        verify(lawRepository, times(1)).findByCategory(category);
    }
}
