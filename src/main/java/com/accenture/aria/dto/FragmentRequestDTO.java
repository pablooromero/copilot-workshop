package com.accenture.aria.dto;

import com.accenture.aria.model.FragmentType;
import com.accenture.aria.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FragmentRequestDTO {
    
    @NotNull(message = "Type is required")
    private FragmentType type;
    
    @NotBlank(message = "Text is required")
    @Size(max = 500, message = "Text must be less than 500 characters")
    private String text;
    
    @NotNull(message = "Role is required")
    private Role role;
}
