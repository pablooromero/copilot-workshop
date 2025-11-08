package com.accenture.aria.dto;

import com.accenture.aria.model.Priority;
import com.accenture.aria.model.Status;

import java.time.LocalDateTime;

public class TicketResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private PersonResponseDTO reporter;
    private PersonResponseDTO assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TicketResponseDTO() {}

    public TicketResponseDTO(Long id, String title, String description, Status status, Priority priority, PersonResponseDTO reporter, PersonResponseDTO assignee, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.reporter = reporter;
        this.assignee = assignee;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public PersonResponseDTO getReporter() { return reporter; }
    public void setReporter(PersonResponseDTO reporter) { this.reporter = reporter; }

    public PersonResponseDTO getAssignee() { return assignee; }
    public void setAssignee(PersonResponseDTO assignee) { this.assignee = assignee; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Builder para compatibilidad
    public static TicketResponseDTOBuilder builder() { return new TicketResponseDTOBuilder(); }

    public static class TicketResponseDTOBuilder {
        private Long id;
        private String title;
        private String description;
        private Status status;
        private Priority priority;
        private PersonResponseDTO reporter;
        private PersonResponseDTO assignee;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public TicketResponseDTOBuilder id(Long id) { this.id = id; return this; }
        public TicketResponseDTOBuilder title(String title) { this.title = title; return this; }
        public TicketResponseDTOBuilder description(String description) { this.description = description; return this; }
        public TicketResponseDTOBuilder status(Status status) { this.status = status; return this; }
        public TicketResponseDTOBuilder priority(Priority priority) { this.priority = priority; return this; }
        public TicketResponseDTOBuilder reporter(PersonResponseDTO reporter) { this.reporter = reporter; return this; }
        public TicketResponseDTOBuilder assignee(PersonResponseDTO assignee) { this.assignee = assignee; return this; }
        public TicketResponseDTOBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public TicketResponseDTOBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public TicketResponseDTO build() {
            return new TicketResponseDTO(id, title, description, status, priority, reporter, assignee, createdAt, updatedAt);
        }
    }
}
