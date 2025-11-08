package com.accenture.aria.dto;

import java.time.LocalDateTime;

public class PersonResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String username;
    private String department;
    private String role;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PersonResponseDTO() {}

    public PersonResponseDTO(Long id, String firstName, String lastName, String fullName, String email, String username, String department, String role, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.department = department;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Builder
    public static PersonResponseDTOBuilder builder() { return new PersonResponseDTOBuilder(); }

    public static class PersonResponseDTOBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String fullName;
        private String email;
        private String username;
        private String department;
        private String role;
        private Boolean active;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PersonResponseDTOBuilder id(Long id) { this.id = id; return this; }
        public PersonResponseDTOBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public PersonResponseDTOBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public PersonResponseDTOBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public PersonResponseDTOBuilder email(String email) { this.email = email; return this; }
        public PersonResponseDTOBuilder username(String username) { this.username = username; return this; }
        public PersonResponseDTOBuilder department(String department) { this.department = department; return this; }
        public PersonResponseDTOBuilder role(String role) { this.role = role; return this; }
        public PersonResponseDTOBuilder active(Boolean active) { this.active = active; return this; }
        public PersonResponseDTOBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public PersonResponseDTOBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public PersonResponseDTO build() {
            return new PersonResponseDTO(id, firstName, lastName, fullName, email, username, department, role, active, createdAt, updatedAt);
        }
    }
}
