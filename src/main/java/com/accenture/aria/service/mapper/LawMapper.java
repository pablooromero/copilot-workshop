package com.accenture.aria.service.mapper;

import com.accenture.aria.dto.LawRequestDTO;
import com.accenture.aria.dto.LawResponseDTO;
import com.accenture.aria.model.Law;

public class LawMapper {
    
    private LawMapper() {
        // Constructor privado para evitar instanciación
    }
    
    public static Law toEntity(LawRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Law.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .build();
    }
    
    public static LawResponseDTO toResponse(Law law) {
        if (law == null) {
            return null;
        }
        
        return LawResponseDTO.builder()
                .id(law.getId())
                .name(law.getName())
                .description(law.getDescription())
                .category(law.getCategory())
                .build();
    }
}
