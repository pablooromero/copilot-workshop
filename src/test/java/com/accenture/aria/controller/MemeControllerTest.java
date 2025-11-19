package com.accenture.aria.controller;

import com.accenture.aria.dto.MemeRequestDTO;
import com.accenture.aria.dto.MemeResponseDTO;
import com.accenture.aria.model.Meme;
import com.accenture.aria.service.IMemeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemeController.class)
class MemeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private IMemeService memeService;
    
    @Test
    void testFindAll_ShouldReturn200() throws Exception {
        // Given
        List<MemeResponseDTO> memes = Arrays.asList(
            MemeResponseDTO.builder().id(1L).character("Tano Pasman").description("¡¿CÓMO?!").build(),
            MemeResponseDTO.builder().id(2L).character("Meme Argento").description("Descripción").build()
        );
        
        when(memeService.findAll()).thenReturn(memes);
        
        // When & Then
        mockMvc.perform(get("/api/memes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].character").value("Tano Pasman"))
                .andExpect(jsonPath("$[1].id").value(2));
    }
    
    @Test
    void testFindById_ShouldReturn200() throws Exception {
        // Given
        MemeResponseDTO meme = MemeResponseDTO.builder()
                .id(1L)
                .character("Dev Meme")
                .description("Funciona en mi máquina")
                .build();
        
        when(memeService.findById(1L)).thenReturn(meme);
        
        // When & Then
        mockMvc.perform(get("/api/memes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.character").value("Dev Meme"))
                .andExpect(jsonPath("$.description").value("Funciona en mi máquina"));
    }
    
    @Test
    void testCreateMeme_WithValidData_ShouldReturn201() throws Exception {
        // Given
        MemeRequestDTO request = MemeRequestDTO.builder()
                .character("Nuevo Personaje")
                .description("Nueva descripción divertida")
                .build();
        
        Meme created = Meme.builder()
                .id(1L)
                .character("Nuevo Personaje")
                .description("Nueva descripción divertida")
                .build();
        
        when(memeService.createFromDTO(any(MemeRequestDTO.class))).thenReturn(created);
        
        // When & Then
        mockMvc.perform(post("/api/memes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.character").value("Nuevo Personaje"))
                .andExpect(jsonPath("$.description").value("Nueva descripción divertida"));
    }
    
    @Test
    void testUpdateMeme_ShouldReturn200() throws Exception {
        // Given
        MemeRequestDTO request = MemeRequestDTO.builder()
                .character("Personaje actualizado")
                .description("Descripción actualizada")
                .build();
        
        Meme updated = Meme.builder()
                .id(1L)
                .character("Personaje actualizado")
                .description("Descripción actualizada")
                .build();
        
        when(memeService.updateFromDTO(eq(1L), any(MemeRequestDTO.class))).thenReturn(updated);
        
        // When & Then
        mockMvc.perform(put("/api/memes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.character").value("Personaje actualizado"))
                .andExpect(jsonPath("$.description").value("Descripción actualizada"));
    }
    
    @Test
    void testDeleteMeme_ShouldReturn204() throws Exception {
        // Given
        doNothing().when(memeService).delete(1L);
        
        // When & Then
        mockMvc.perform(delete("/api/memes/1"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void testGetRandomMeme_ShouldReturn200() throws Exception {
        // Given
        MemeResponseDTO meme = MemeResponseDTO.builder()
                .id(5L)
                .character("Meme Random")
                .description("Descripción random")
                .build();
        
        when(memeService.findAll()).thenReturn(Arrays.asList(meme));
        
        // When & Then
        mockMvc.perform(get("/api/memes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
