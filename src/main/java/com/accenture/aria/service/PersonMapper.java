package com.accenture.aria.service;

import com.accenture.aria.dto.PersonRequestDTO;
import com.accenture.aria.dto.PersonResponseDTO;
import com.accenture.aria.model.Person;

public class PersonMapper {

    private PersonMapper() {
        // Private constructor to hide implicit public one
    }

    public static PersonResponseDTO toResponse(Person person) {
        if (person == null) {
            return null;
        }
        return PersonResponseDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .fullName(person.getFullName())
                .email(person.getEmail())
                .username(person.getUsername())
                .department(person.getDepartment())
                .role(person.getRole())
                .active(person.getActive())
                .createdAt(person.getCreatedAt())
                .updatedAt(person.getUpdatedAt())
                .build();
    }

    public static Person toEntity(PersonRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Person.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .department(dto.getDepartment())
                .role(dto.getRole())
                .active(dto.getActive())
                .build();
    }
}
