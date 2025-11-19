package com.accenture.aria.service;

import com.accenture.aria.dto.FragmentRequestDTO;
import com.accenture.aria.dto.FragmentResponseDTO;
import com.accenture.aria.model.Fragment;
import com.accenture.aria.model.FragmentType;
import com.accenture.aria.model.Role;

import java.util.List;

public interface IFragmentService {
    Fragment create(Fragment fragment);
    Fragment createFromDTO(FragmentRequestDTO dto);
    FragmentResponseDTO findById(Long id);
    List<FragmentResponseDTO> findAll();
    List<FragmentResponseDTO> findByType(FragmentType type);
    List<FragmentResponseDTO> findByRole(Role role);
    Fragment update(Long id, Fragment fragment);
    Fragment updateFromDTO(Long id, FragmentRequestDTO dto);
    void delete(Long id);
}
