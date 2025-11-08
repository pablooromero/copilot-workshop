package com.accenture.aria.dto;

import com.accenture.aria.model.Priority;
import com.accenture.aria.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TicketRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 5000)
    private String description;

    private Status status;

    private Priority priority;

    private Long reporterId;

    private Long assigneeId;

    public TicketRequestDTO() {}

    public TicketRequestDTO(String title, String description, Status status, Priority priority, Long reporterId, Long assigneeId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
    }

    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Long getReporterId() { return reporterId; }
    public void setReporterId(Long reporterId) { this.reporterId = reporterId; }

    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }

    // Builder para compatibilidad
    public static TicketRequestDTOBuilder builder() { return new TicketRequestDTOBuilder(); }

    public static class TicketRequestDTOBuilder {
        private String title;
        private String description;
        private Status status;
        private Priority priority;
        private Long reporterId;
        private Long assigneeId;

        public TicketRequestDTOBuilder title(String title) { this.title = title; return this; }
        public TicketRequestDTOBuilder description(String description) { this.description = description; return this; }
        public TicketRequestDTOBuilder status(Status status) { this.status = status; return this; }
        public TicketRequestDTOBuilder priority(Priority priority) { this.priority = priority; return this; }
        public TicketRequestDTOBuilder reporterId(Long reporterId) { this.reporterId = reporterId; return this; }
        public TicketRequestDTOBuilder assigneeId(Long assigneeId) { this.assigneeId = assigneeId; return this; }

        public TicketRequestDTO build() {
            return new TicketRequestDTO(title, description, status, priority, reporterId, assigneeId);
        }
    }
}
