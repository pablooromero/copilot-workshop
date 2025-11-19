package com.accenture.aria.service;

import com.accenture.aria.dto.ExcuseAIRequestDTO;
import com.accenture.aria.dto.ExcuseResponseDTO;
import com.accenture.aria.model.Role;

public interface IExcuseService {
    ExcuseResponseDTO generateExcuse(Long seed, Role role);
    ExcuseResponseDTO generateExcuseWithMeme(Long seed, Role role);
    ExcuseResponseDTO generateExcuseWithLaw(Long seed, Role role);
    ExcuseResponseDTO generateExcuseUltra(Long seed, Role role);
    
    /**
     * Genera una excusa usando Google Gemini AI.
     * Si la API falla, hace fallback a generación tradicional.
     * 
     * @param request Parámetros de generación AI (role, context, creativity, etc.)
     * @return Excusa generada por IA o tradicional si falla
     */
    ExcuseResponseDTO generateExcuseWithAI(ExcuseAIRequestDTO request);
}
