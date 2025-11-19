package com.accenture.aria.service;

import com.accenture.aria.dto.MemeRequestDTO;
import com.accenture.aria.dto.MemeResponseDTO;
import com.accenture.aria.model.Meme;

import java.util.List;

public interface IMemeService {
    Meme create(Meme meme);
    Meme createFromDTO(MemeRequestDTO dto);
    MemeResponseDTO findById(Long id);
    List<MemeResponseDTO> findAll();
    Meme update(Long id, Meme meme);
    Meme updateFromDTO(Long id, MemeRequestDTO dto);
    void delete(Long id);
}
