package com.accenture.aria.dto;

import com.accenture.aria.model.FragmentType;
import com.accenture.aria.model.Role;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FragmentResponseDTO {
    private Long id;
    private FragmentType type;
    private String text;
    private Role role;
}
