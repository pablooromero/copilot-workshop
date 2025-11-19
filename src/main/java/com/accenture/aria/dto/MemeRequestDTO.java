package com.accenture.aria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemeRequestDTO {
    
    @NotBlank(message = "Character is required")
    @Size(max = 200, message = "Character must be less than 200 characters")
    private String character;
    
    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
}
