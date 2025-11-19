package com.accenture.aria.service.mapper;

import com.accenture.aria.dto.FragmentRequestDTO;
import com.accenture.aria.dto.FragmentResponseDTO;
import com.accenture.aria.model.Fragment;

public class FragmentMapper {
    
    private FragmentMapper() {
        // Constructor privado para evitar instanciación
    }
    
    public static Fragment toEntity(FragmentRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Fragment.builder()
                .type(dto.getType())
                .text(dto.getText())
                .role(dto.getRole())
                .build();
    }
    
    public static FragmentResponseDTO toResponse(Fragment fragment) {
        if (fragment == null) {
            return null;
        }
        
        return FragmentResponseDTO.builder()
                .id(fragment.getId())
                .type(fragment.getType())
                .text(fragment.getText())
                .role(fragment.getRole())
                .build();
    }
}
