package com.accenture.aria.service.mapper;

import com.accenture.aria.dto.MemeRequestDTO;
import com.accenture.aria.dto.MemeResponseDTO;
import com.accenture.aria.model.Meme;

public class MemeMapper {
    
    private MemeMapper() {
        // Constructor privado para evitar instanciación
    }
    
    public static Meme toEntity(MemeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Meme.builder()
                .character(dto.getCharacter())
                .description(dto.getDescription())
                .build();
    }
    
    public static MemeResponseDTO toResponse(Meme meme) {
        if (meme == null) {
            return null;
        }
        
        return MemeResponseDTO.builder()
                .id(meme.getId())
                .character(meme.getCharacter())
                .description(meme.getDescription())
                .build();
    }
}
