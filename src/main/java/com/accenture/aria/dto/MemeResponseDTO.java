package com.accenture.aria.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemeResponseDTO {
    private Long id;
    private String character;
    private String description;
}
