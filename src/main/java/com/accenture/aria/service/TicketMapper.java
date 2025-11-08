package com.accenture.aria.service;

import com.accenture.aria.dto.TicketRequestDTO;
import com.accenture.aria.dto.TicketResponseDTO;
import com.accenture.aria.model.Ticket;

public class TicketMapper {

    private TicketMapper() {
        // Private constructor to hide implicit public one
    }

    public static Ticket toEntity(TicketRequestDTO dto) {
        if (dto == null) return null;
        Ticket.TicketBuilder builder = Ticket.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority());
        
        // Note: reporter and assignee need to be set separately in the service
        // after fetching Person entities by their IDs
        
        return builder.build();
    }

    public static TicketResponseDTO toResponse(Ticket t) {
        if (t == null) return null;
        return TicketResponseDTO.builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .status(t.getStatus())
                .priority(t.getPriority())
                .reporter(PersonMapper.toResponse(t.getReporter()))
                .assignee(PersonMapper.toResponse(t.getAssignee()))
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
    }
}
