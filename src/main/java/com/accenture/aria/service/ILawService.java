package com.accenture.aria.service;

import com.accenture.aria.dto.LawRequestDTO;
import com.accenture.aria.dto.LawResponseDTO;
import com.accenture.aria.model.Law;
import com.accenture.aria.model.LawCategory;

import java.util.List;

public interface ILawService {
    Law create(Law law);
    Law createFromDTO(LawRequestDTO dto);
    LawResponseDTO findById(Long id);
    List<LawResponseDTO> findAll();
    List<LawResponseDTO> findByCategory(LawCategory category);
    Law update(Long id, Law law);
    Law updateFromDTO(Long id, LawRequestDTO dto);
    void delete(Long id);
}
