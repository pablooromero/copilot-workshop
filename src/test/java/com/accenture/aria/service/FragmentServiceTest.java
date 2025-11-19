package com.accenture.aria.service;

import com.accenture.aria.dto.FragmentRequestDTO;
import com.accenture.aria.dto.FragmentResponseDTO;
import com.accenture.aria.exception.ResourceNotFoundException;
import com.accenture.aria.model.Fragment;
import com.accenture.aria.model.FragmentType;
import com.accenture.aria.model.Role;
import com.accenture.aria.repository.FragmentRepository;
import com.accenture.aria.service.implementations.FragmentService;
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

class FragmentServiceTest {
    
    @Mock
    private FragmentRepository fragmentRepository;
    
    private IFragmentService fragmentService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fragmentService = new FragmentService(fragmentRepository);
    }
    
    @Test
    void testCreate_ShouldSaveFragment() {
        // Given
        Fragment fragment = Fragment.builder()
                .type(FragmentType.CONTEXTO)
                .text("Durante el deploy")
                .role(Role.DEV)
                .build();
        
        Fragment savedFragment = Fragment.builder()
                .id(1L)
                .type(FragmentType.CONTEXTO)
                .text("Durante el deploy")
                .role(Role.DEV)
                .build();
        
        when(fragmentRepository.save(any(Fragment.class))).thenReturn(savedFragment);
        
        // When
        Fragment result = fragmentService.create(fragment);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(FragmentType.CONTEXTO, result.getType());
        assertEquals("Durante el deploy", result.getText());
        assertEquals(Role.DEV, result.getRole());
        verify(fragmentRepository, times(1)).save(fragment);
    }
    
    @Test
    void testCreateFromDTO_ShouldConvertAndSave() {
        // Given
        FragmentRequestDTO dto = FragmentRequestDTO.builder()
                .type(FragmentType.CAUSA)
                .text("el servidor estaba caído")
                .role(Role.DEVOPS)
                .build();
        
        Fragment savedFragment = Fragment.builder()
                .id(2L)
                .type(FragmentType.CAUSA)
                .text("el servidor estaba caído")
                .role(Role.DEVOPS)
                .build();
        
        when(fragmentRepository.save(any(Fragment.class))).thenReturn(savedFragment);
        
        // When
        Fragment result = fragmentService.createFromDTO(dto);
        
        // Then
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(FragmentType.CAUSA, result.getType());
        assertEquals("el servidor estaba caído", result.getText());
        verify(fragmentRepository, times(1)).save(any(Fragment.class));
    }
    
    @Test
    void testFindById_WhenExists_ShouldReturnFragment() {
        // Given
        Long fragmentId = 1L;
        Fragment fragment = Fragment.builder()
                .id(fragmentId)
                .type(FragmentType.CONSECUENCIA)
                .text("tuvimos que hacer rollback")
                .role(Role.ALL)
                .build();
        
        when(fragmentRepository.findById(fragmentId)).thenReturn(Optional.of(fragment));
        
        // When
        FragmentResponseDTO result = fragmentService.findById(fragmentId);
        
        // Then
        assertNotNull(result);
        assertEquals(fragmentId, result.getId());
        assertEquals(FragmentType.CONSECUENCIA, result.getType());
        assertEquals("tuvimos que hacer rollback", result.getText());
        assertEquals(Role.ALL, result.getRole());
        verify(fragmentRepository, times(1)).findById(fragmentId);
    }
    
    @Test
    void testFindById_WhenNotExists_ShouldThrowException() {
        // Given
        Long fragmentId = 999L;
        when(fragmentRepository.findById(fragmentId)).thenReturn(Optional.empty());
        
        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            fragmentService.findById(fragmentId);
        });
        
        assertTrue(exception.getMessage().contains("Fragment not found with id: 999"));
        verify(fragmentRepository, times(1)).findById(fragmentId);
    }
    
    @Test
    void testFindAll_ShouldReturnAllFragments() {
        // Given
        List<Fragment> fragments = Arrays.asList(
            Fragment.builder().id(1L).type(FragmentType.CONTEXTO).text("Contexto 1").role(Role.DEV).build(),
            Fragment.builder().id(2L).type(FragmentType.CAUSA).text("Causa 1").role(Role.QA).build(),
            Fragment.builder().id(3L).type(FragmentType.CONSECUENCIA).text("Consecuencia 1").role(Role.PM).build()
        );
        
        when(fragmentRepository.findAll()).thenReturn(fragments);
        
        // When
        List<FragmentResponseDTO> result = fragmentService.findAll();
        
        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(FragmentType.CONTEXTO, result.get(0).getType());
        assertEquals(2L, result.get(1).getId());
        assertEquals(FragmentType.CAUSA, result.get(1).getType());
        verify(fragmentRepository, times(1)).findAll();
    }
    
    @Test
    void testUpdate_ShouldModifyExistingFragment() {
        // Given
        Long fragmentId = 1L;
        Fragment existing = Fragment.builder()
                .id(fragmentId)
                .type(FragmentType.CONTEXTO)
                .text("Texto original")
                .role(Role.DEV)
                .build();
        
        Fragment updates = Fragment.builder()
                .type(FragmentType.CAUSA)
                .text("Texto actualizado")
                .role(Role.QA)
                .build();
        
        Fragment updated = Fragment.builder()
                .id(fragmentId)
                .type(FragmentType.CAUSA)
                .text("Texto actualizado")
                .role(Role.QA)
                .build();
        
        when(fragmentRepository.findById(fragmentId)).thenReturn(Optional.of(existing));
        when(fragmentRepository.save(any(Fragment.class))).thenReturn(updated);
        
        // When
        Fragment result = fragmentService.update(fragmentId, updates);
        
        // Then
        assertNotNull(result);
        assertEquals(FragmentType.CAUSA, result.getType());
        assertEquals("Texto actualizado", result.getText());
        assertEquals(Role.QA, result.getRole());
        verify(fragmentRepository, times(1)).findById(fragmentId);
        verify(fragmentRepository, times(1)).save(any(Fragment.class));
    }
    
    @Test
    void testUpdate_WithNullFields_ShouldKeepOriginalValues() {
        // Given
        Long fragmentId = 1L;
        Fragment existing = Fragment.builder()
                .id(fragmentId)
                .type(FragmentType.CONTEXTO)
                .text("Texto original")
                .role(Role.DEV)
                .build();
        
        // Solo actualizar el texto, dejar type y role en null
        Fragment updates = Fragment.builder()
                .text("Solo texto nuevo")
                .build();
        
        when(fragmentRepository.findById(fragmentId)).thenReturn(Optional.of(existing));
        when(fragmentRepository.save(any(Fragment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // When
        Fragment result = fragmentService.update(fragmentId, updates);
        
        // Then
        assertNotNull(result);
        assertEquals(FragmentType.CONTEXTO, result.getType(), "Type should remain unchanged");
        assertEquals("Solo texto nuevo", result.getText(), "Text should be updated");
        assertEquals(Role.DEV, result.getRole(), "Role should remain unchanged");
        verify(fragmentRepository, times(1)).save(any(Fragment.class));
    }
    
    @Test
    void testDelete_ShouldRemoveFragment() {
        // Given
        Long fragmentId = 1L;
        when(fragmentRepository.existsById(fragmentId)).thenReturn(true);
        doNothing().when(fragmentRepository).deleteById(fragmentId);
        
        // When
        fragmentService.delete(fragmentId);
        
        // Then
        verify(fragmentRepository, times(1)).existsById(fragmentId);
        verify(fragmentRepository, times(1)).deleteById(fragmentId);
    }
    
    @Test
    void testGetByType_ShouldReturnFilteredFragments() {
        // Given
        FragmentType type = FragmentType.CONTEXTO;
        List<Fragment> fragments = Arrays.asList(
            Fragment.builder().id(1L).type(type).text("Contexto 1").role(Role.DEV).build(),
            Fragment.builder().id(2L).type(type).text("Contexto 2").role(Role.QA).build()
        );
        
        when(fragmentRepository.findByType(type)).thenReturn(fragments);
        
        // When
        List<FragmentResponseDTO> result = fragmentService.findByType(type);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(f -> f.getType() == FragmentType.CONTEXTO));
        verify(fragmentRepository, times(1)).findByType(type);
    }
    
    @Test
    void testGetByRole_ShouldReturnFilteredFragments() {
        // Given
        Role role = Role.DEVOPS;
        List<Fragment> fragments = Arrays.asList(
            Fragment.builder().id(1L).type(FragmentType.CAUSA).text("Causa DevOps").role(role).build(),
            Fragment.builder().id(2L).type(FragmentType.CONSECUENCIA).text("Consecuencia DevOps").role(role).build()
        );
        
        when(fragmentRepository.findByRole(role)).thenReturn(fragments);
        
        // When
        List<FragmentResponseDTO> result = fragmentService.findByRole(role);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(f -> f.getRole() == Role.DEVOPS));
        verify(fragmentRepository, times(1)).findByRole(role);
    }
}
