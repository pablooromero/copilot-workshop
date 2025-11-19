package com.accenture.aria.dto;

import com.accenture.aria.model.LawCategory;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LawResponseDTO {
    private Long id;
    private String name;
    private String description;
    private LawCategory category;
}
