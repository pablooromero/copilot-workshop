package com.accenture.aria.controller;

import com.accenture.aria.dto.ExcuseResponseDTO;
import com.accenture.aria.model.Role;
import com.accenture.aria.service.IExcuseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExcuseController.class)
class ExcuseControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private IExcuseService excuseService;
    
    @Test
    void testGetRandomExcuse_ShouldReturn200() throws Exception {
        // Given
        ExcuseResponseDTO excuse = ExcuseResponseDTO.builder()
                .contexto("Durante el deploy")
                .causa("el servidor estaba caído")
                .consecuencia("tuvimos que hacer rollback")
                .recomendacion("implementar monitoreo proactivo")
                .build();
        
        when(excuseService.generateExcuse(isNull(), isNull())).thenReturn(excuse);
        
        // When & Then
        mockMvc.perform(get("/api/excuses/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contexto").value("Durante el deploy"))
                .andExpect(jsonPath("$.causa").value("el servidor estaba caído"))
                .andExpect(jsonPath("$.consecuencia").value("tuvimos que hacer rollback"))
                .andExpect(jsonPath("$.recomendacion").value("implementar monitoreo proactivo"));
    }
    
    @Test
    void testGetRandomExcuseWithSeed_ShouldReturn200() throws Exception {
        // Given
        Long seed = 123L;
        ExcuseResponseDTO excuse = ExcuseResponseDTO.builder()
                .contexto("Contexto con seed")
                .causa("Causa con seed")
                .consecuencia("Consecuencia con seed")
                .recomendacion("Recomendación con seed")
                .build();
        
        when(excuseService.generateExcuse(eq(seed), isNull())).thenReturn(excuse);
        
        // When & Then
        mockMvc.perform(get("/api/excuses/random").param("seed", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contexto").value("Contexto con seed"));
    }
    
    @Test
    void testGetDailyExcuse_ShouldReturn200() throws Exception {
        // Given
        ExcuseResponseDTO excuse = ExcuseResponseDTO.builder()
                .contexto("Excusa del día")
                .causa("Causa diaria")
                .consecuencia("Consecuencia diaria")
                .recomendacion("Recomendación diaria")
                .build();
        
        when(excuseService.generateExcuse(anyLong(), isNull())).thenReturn(excuse);
        
        // When & Then
        mockMvc.perform(get("/api/excuses/daily"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contexto").value("Excusa del día"));
    }
    
    @Test
    void testGetExcuseByRole_ShouldReturn200() throws Exception {
        // Given
        ExcuseResponseDTO excuse = ExcuseResponseDTO.builder()
                .contexto("Excusa para DEV")
                .causa("Causa DEV")
                .consecuencia("Consecuencia DEV")
                .recomendacion("Recomendación DEV")
                .build();
        
        when(excuseService.generateExcuse(isNull(), eq(Role.DEV))).thenReturn(excuse);
        
        // When & Then
        mockMvc.perform(get("/api/excuses/role/DEV"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contexto").value("Excusa para DEV"));
    }
    
    @Test
    void testGetExcuseWithMeme_ShouldReturn200() throws Exception {
        // Given
        ExcuseResponseDTO excuse = ExcuseResponseDTO.builder()
                .contexto("Contexto")
                .causa("Causa")
                .consecuencia("Consecuencia")
                .recomendacion("Recomendación")
                .build();
        
        when(excuseService.generateExcuseWithMeme(isNull(), isNull())).thenReturn(excuse);
        
        // When & Then
        mockMvc.perform(get("/api/excuses/meme"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contexto").value("Contexto"));
    }
    
    @Test
    void testGetExcuseWithLaw_ShouldReturn200() throws Exception {
        // Given
        ExcuseResponseDTO excuse = ExcuseResponseDTO.builder()
                .contexto("Contexto")
                .causa("Causa")
                .consecuencia("Consecuencia")
                .recomendacion("Recomendación")
                .build();
        
        when(excuseService.generateExcuseWithLaw(isNull(), isNull())).thenReturn(excuse);
        
        // When & Then
        mockMvc.perform(get("/api/excuses/law"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contexto").value("Contexto"));
    }
    
    @Test
    void testGetExcuseUltra_ShouldReturn200() throws Exception {
        // Given
        ExcuseResponseDTO excuse = ExcuseResponseDTO.builder()
                .contexto("Contexto ultra")
                .causa("Causa ultra")
                .consecuencia("Consecuencia ultra")
                .recomendacion("Recomendación ultra")
                .build();
        
        when(excuseService.generateExcuseUltra(isNull(), isNull())).thenReturn(excuse);
        
        // When & Then
        mockMvc.perform(get("/api/excuses/ultra"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contexto").value("Contexto ultra"));
    }
}
