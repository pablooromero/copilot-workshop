package com.accenture.aria.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcuseResponseDTO {
    private String contexto;
    private String causa;
    private String consecuencia;
    private String recomendacion;
    private MemeResponseDTO meme;
    private LawResponseDTO law;
}
