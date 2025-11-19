package com.accenture.aria.service.mapper;

import com.accenture.aria.dto.FragmentRequestDTO;
import com.accenture.aria.dto.FragmentResponseDTO;
import com.accenture.aria.dto.MemeRequestDTO;
import com.accenture.aria.dto.MemeResponseDTO;
import com.accenture.aria.dto.LawRequestDTO;
import com.accenture.aria.dto.LawResponseDTO;
import com.accenture.aria.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {
    
    // ===== Fragment Mapper Tests =====
    
    @Test
    void testFragmentMapper_ToEntity() {
        // Given
        FragmentRequestDTO dto = FragmentRequestDTO.builder()
                .type(FragmentType.CONTEXTO)
                .text("Durante el deploy del viernes")
                .role(Role.DEV)
                .build();
        
        // When
        Fragment entity = FragmentMapper.toEntity(dto);
        
        // Then
        assertNotNull(entity);
        assertEquals(FragmentType.CONTEXTO, entity.getType());
        assertEquals("Durante el deploy del viernes", entity.getText());
        assertEquals(Role.DEV, entity.getRole());
        assertNull(entity.getId(), "ID should be null for new entities");
    }
    
    @Test
    void testFragmentMapper_ToResponse() {
        // Given
        Fragment entity = Fragment.builder()
                .id(1L)
                .type(FragmentType.CAUSA)
                .text("el servidor se quedó sin memoria")
                .role(Role.DEVOPS)
                .build();
        
        // When
        FragmentResponseDTO dto = FragmentMapper.toResponse(entity);
        
        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(FragmentType.CAUSA, dto.getType());
        assertEquals("el servidor se quedó sin memoria", dto.getText());
        assertEquals(Role.DEVOPS, dto.getRole());
    }
    
    // ===== Meme Mapper Tests =====
    
    @Test
    void testMemeMapper_ToEntity() {
        // Given
        MemeRequestDTO dto = MemeRequestDTO.builder()
                .character("Tano Pasman")
                .description("¡¿CÓMO QUE CAYÓ PRODUCCIÓN?!")
                .build();
        
        // When
        Meme entity = MemeMapper.toEntity(dto);
        
        // Then
        assertNotNull(entity);
        assertEquals("Tano Pasman", entity.getCharacter());
        assertEquals("¡¿CÓMO QUE CAYÓ PRODUCCIÓN?!", entity.getDescription());
        assertNull(entity.getId(), "ID should be null for new entities");
    }
    
    @Test
    void testMemeMapper_ToResponse() {
        // Given
        Meme entity = Meme.builder()
                .id(2L)
                .character("Meme Argento")
                .description("Con un 'funciona en mi máquina' no alcanza")
                .build();
        
        // When
        MemeResponseDTO dto = MemeMapper.toResponse(entity);
        
        // Then
        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertEquals("Meme Argento", dto.getCharacter());
        assertEquals("Con un 'funciona en mi máquina' no alcanza", dto.getDescription());
    }
    
    // ===== Law Mapper Tests =====
    
    @Test
    void testLawMapper_ToEntity() {
        // Given
        LawRequestDTO dto = LawRequestDTO.builder()
                .name("Ley de Murphy para Devs")
                .description("Si algo puede fallar en producción, fallará justo después del deploy")
                .category(LawCategory.MURPHY)
                .build();
        
        // When
        Law entity = LawMapper.toEntity(dto);
        
        // Then
        assertNotNull(entity);
        assertEquals("Ley de Murphy para Devs", entity.getName());
        assertEquals("Si algo puede fallar en producción, fallará justo después del deploy", entity.getDescription());
        assertEquals(LawCategory.MURPHY, entity.getCategory());
        assertNull(entity.getId(), "ID should be null for new entities");
    }
    
    @Test
    void testLawMapper_ToResponse() {
        // Given
        Law entity = Law.builder()
                .id(3L)
                .name("Principio de Hofstadter")
                .description("Siempre lleva más tiempo de lo que piensas, incluso cuando consideras este principio")
                .category(LawCategory.HOFSTADTER)
                .build();
        
        // When
        LawResponseDTO dto = LawMapper.toResponse(entity);
        
        // Then
        assertNotNull(dto);
        assertEquals(3L, dto.getId());
        assertEquals("Principio de Hofstadter", dto.getName());
        assertEquals("Siempre lleva más tiempo de lo que piensas, incluso cuando consideras este principio", dto.getDescription());
        assertEquals(LawCategory.HOFSTADTER, dto.getCategory());
    }
}
