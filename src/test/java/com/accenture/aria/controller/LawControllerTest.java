package com.accenture.aria.controller;

import com.accenture.aria.dto.LawRequestDTO;
import com.accenture.aria.dto.LawResponseDTO;
import com.accenture.aria.model.Law;
import com.accenture.aria.model.LawCategory;
import com.accenture.aria.service.ILawService;
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

@WebMvcTest(LawController.class)
class LawControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ILawService lawService;
    
    @Test
    void testFindAll_ShouldReturn200() throws Exception {
        // Given
        List<LawResponseDTO> laws = Arrays.asList(
            LawResponseDTO.builder().id(1L).name("Ley de Murphy").description("Desc 1").category(LawCategory.MURPHY).build(),
            LawResponseDTO.builder().id(2L).name("Ley de Hofstadter").description("Desc 2").category(LawCategory.HOFSTADTER).build()
        );
        
        when(lawService.findAll()).thenReturn(laws);
        
        // When & Then
        mockMvc.perform(get("/api/laws"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Ley de Murphy"))
                .andExpect(jsonPath("$[1].id").value(2));
    }
    
    @Test
    void testFindById_ShouldReturn200() throws Exception {
        // Given
        LawResponseDTO law = LawResponseDTO.builder()
                .id(1L)
                .name("Principio de Dilbert")
                .description("Los incompetentes son promovidos")
                .category(LawCategory.DILBERT)
                .build();
        
        when(lawService.findById(1L)).thenReturn(law);
        
        // When & Then
        mockMvc.perform(get("/api/laws/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Principio de Dilbert"))
                .andExpect(jsonPath("$.category").value("DILBERT"));
    }
    
    @Test
    void testFindByCategory_ShouldReturn200() throws Exception {
        // Given
        List<LawResponseDTO> laws = Arrays.asList(
            LawResponseDTO.builder().id(1L).name("Murphy 1").description("Desc 1").category(LawCategory.MURPHY).build(),
            LawResponseDTO.builder().id(2L).name("Murphy 2").description("Desc 2").category(LawCategory.MURPHY).build()
        );
        
        when(lawService.findByCategory(LawCategory.MURPHY)).thenReturn(laws);
        
        // When & Then
        mockMvc.perform(get("/api/laws/category/MURPHY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value("MURPHY"))
                .andExpect(jsonPath("$[1].category").value("MURPHY"));
    }
    
    @Test
    void testCreateLaw_WithValidData_ShouldReturn201() throws Exception {
        // Given
        LawRequestDTO request = LawRequestDTO.builder()
                .name("Nueva Ley DevOps")
                .description("Descripción de principio DevOps")
                .category(LawCategory.DEVOPS)
                .build();
        
        Law created = Law.builder()
                .id(1L)
                .name("Nueva Ley DevOps")
                .description("Descripción de principio DevOps")
                .category(LawCategory.DEVOPS)
                .build();
        
        when(lawService.createFromDTO(any(LawRequestDTO.class))).thenReturn(created);
        
        // When & Then
        mockMvc.perform(post("/api/laws")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nueva Ley DevOps"))
                .andExpect(jsonPath("$.category").value("DEVOPS"));
    }
    
    @Test
    void testUpdateLaw_ShouldReturn200() throws Exception {
        // Given
        LawRequestDTO request = LawRequestDTO.builder()
                .name("Ley actualizada")
                .description("Descripción actualizada")
                .category(LawCategory.AXIOM)
                .build();
        
        Law updated = Law.builder()
                .id(1L)
                .name("Ley actualizada")
                .description("Descripción actualizada")
                .category(LawCategory.AXIOM)
                .build();
        
        when(lawService.updateFromDTO(eq(1L), any(LawRequestDTO.class))).thenReturn(updated);
        
        // When & Then
        mockMvc.perform(put("/api/laws/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ley actualizada"))
                .andExpect(jsonPath("$.category").value("AXIOM"));
    }
    
    @Test
    void testDeleteLaw_ShouldReturn204() throws Exception {
        // Given
        doNothing().when(lawService).delete(1L);
        
        // When & Then
        mockMvc.perform(delete("/api/laws/1"))
                .andExpect(status().isNoContent());
    }
}
