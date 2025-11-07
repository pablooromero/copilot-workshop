package com.accenture.ticketmanager.dto;

import com.accenture.ticketmanager.model.Priority;
import com.accenture.ticketmanager.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 5000)
    private String description;

    private Status status;

    private Priority priority;

    private String reporter;

    private String assignee;
}
