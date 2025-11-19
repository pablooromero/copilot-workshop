package com.accenture.aria.controller;

import com.accenture.aria.dto.FragmentRequestDTO;
import com.accenture.aria.dto.FragmentResponseDTO;
import com.accenture.aria.model.Fragment;
import com.accenture.aria.model.FragmentType;
import com.accenture.aria.model.Role;
import com.accenture.aria.service.IFragmentService;
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

@WebMvcTest(FragmentController.class)
class FragmentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private IFragmentService fragmentService;
    
    @Test
    void testFindAll_ShouldReturn200() throws Exception {
        // Given
        List<FragmentResponseDTO> fragments = Arrays.asList(
            FragmentResponseDTO.builder().id(1L).type(FragmentType.CONTEXTO).text("Texto 1").role(Role.DEV).build(),
            FragmentResponseDTO.builder().id(2L).type(FragmentType.CAUSA).text("Texto 2").role(Role.QA).build()
        );
        
        when(fragmentService.findAll()).thenReturn(fragments);
        
        // When & Then
        mockMvc.perform(get("/api/fragments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].type").value("CONTEXTO"))
                .andExpect(jsonPath("$[1].id").value(2));
    }
    
    @Test
    void testFindById_ShouldReturn200() throws Exception {
        // Given
        FragmentResponseDTO fragment = FragmentResponseDTO.builder()
                .id(1L)
                .type(FragmentType.CONTEXTO)
                .text("Durante el deploy")
                .role(Role.DEV)
                .build();
        
        when(fragmentService.findById(1L)).thenReturn(fragment);
        
        // When & Then
        mockMvc.perform(get("/api/fragments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Durante el deploy"));
    }
    
    @Test
    void testFindByType_ShouldReturn200() throws Exception {
        // Given
        List<FragmentResponseDTO> fragments = Arrays.asList(
            FragmentResponseDTO.builder().id(1L).type(FragmentType.CONTEXTO).text("Contexto 1").role(Role.DEV).build(),
            FragmentResponseDTO.builder().id(2L).type(FragmentType.CONTEXTO).text("Contexto 2").role(Role.QA).build()
        );
        
        when(fragmentService.findByType(FragmentType.CONTEXTO)).thenReturn(fragments);
        
        // When & Then
        mockMvc.perform(get("/api/fragments/type/CONTEXTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].type").value("CONTEXTO"))
                .andExpect(jsonPath("$[1].type").value("CONTEXTO"));
    }
    
    @Test
    void testCreateFragment_WithValidData_ShouldReturn201() throws Exception {
        // Given
        FragmentRequestDTO request = FragmentRequestDTO.builder()
                .type(FragmentType.CAUSA)
                .text("el servidor estaba caído")
                .role(Role.DEVOPS)
                .build();
        
        Fragment created = Fragment.builder()
                .id(1L)
                .type(FragmentType.CAUSA)
                .text("el servidor estaba caído")
                .role(Role.DEVOPS)
                .build();
        
        when(fragmentService.createFromDTO(any(FragmentRequestDTO.class))).thenReturn(created);
        
        // When & Then
        mockMvc.perform(post("/api/fragments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("el servidor estaba caído"));
    }
    
    @Test
    void testUpdateFragment_ShouldReturn200() throws Exception {
        // Given
        FragmentRequestDTO request = FragmentRequestDTO.builder()
                .type(FragmentType.CAUSA)
                .text("texto actualizado")
                .role(Role.DEV)
                .build();
        
        Fragment updated = Fragment.builder()
                .id(1L)
                .type(FragmentType.CAUSA)
                .text("texto actualizado")
                .role(Role.DEV)
                .build();
        
        when(fragmentService.updateFromDTO(eq(1L), any(FragmentRequestDTO.class))).thenReturn(updated);
        
        // When & Then
        mockMvc.perform(put("/api/fragments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("texto actualizado"));
    }
    
    @Test
    void testDeleteFragment_ShouldReturn204() throws Exception {
        // Given
        doNothing().when(fragmentService).delete(1L);
        
        // When & Then
        mockMvc.perform(delete("/api/fragments/1"))
                .andExpect(status().isNoContent());
    }
}
