package com.accenture.aria.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Person reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private Person assignee;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Ticket() {
    }

    public Ticket(Long id, String title, String description, Status status, Priority priority, Person reporter, Person assignee, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public Person getReporter() { return reporter; }
    public void setReporter(Person reporter) { this.reporter = reporter; }

    public Person getAssignee() { return assignee; }
    public void setAssignee(Person assignee) { this.assignee = assignee; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Builder pattern simple para compatibilidad con el código existente que usa Ticket.builder()
    public static TicketBuilder builder() {
        return new TicketBuilder();
    }

    public static class TicketBuilder {
        private Long id;
        private String title;
        private String description;
        private Status status;
        private Priority priority;
        private Person reporter;
        private Person assignee;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public TicketBuilder id(Long id) { this.id = id; return this; }
        public TicketBuilder title(String title) { this.title = title; return this; }
        public TicketBuilder description(String description) { this.description = description; return this; }
        public TicketBuilder status(Status status) { this.status = status; return this; }
        public TicketBuilder priority(Priority priority) { this.priority = priority; return this; }
        public TicketBuilder reporter(Person reporter) { this.reporter = reporter; return this; }
        public TicketBuilder assignee(Person assignee) { this.assignee = assignee; return this; }
        public TicketBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public TicketBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Ticket build() {
            return new Ticket(id, title, description, status, priority, reporter, assignee, createdAt, updatedAt);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }
}
