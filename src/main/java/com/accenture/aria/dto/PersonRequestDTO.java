package com.accenture.aria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PersonRequestDTO {

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Size(max = 50)
    private String username;

    @Size(max = 100)
    private String department;

    @Size(max = 50)
    private String role;

    private Boolean active;

    public PersonRequestDTO() {}

    public PersonRequestDTO(String firstName, String lastName, String email, String username, String department, String role, Boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.department = department;
        this.role = role;
        this.active = active;
    }

    // Getters y Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

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

    // Builder
    public static PersonRequestDTOBuilder builder() { return new PersonRequestDTOBuilder(); }

    public static class PersonRequestDTOBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private String username;
        private String department;
        private String role;
        private Boolean active;

        public PersonRequestDTOBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public PersonRequestDTOBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public PersonRequestDTOBuilder email(String email) { this.email = email; return this; }
        public PersonRequestDTOBuilder username(String username) { this.username = username; return this; }
        public PersonRequestDTOBuilder department(String department) { this.department = department; return this; }
        public PersonRequestDTOBuilder role(String role) { this.role = role; return this; }
        public PersonRequestDTOBuilder active(Boolean active) { this.active = active; return this; }

        public PersonRequestDTO build() {
            return new PersonRequestDTO(firstName, lastName, email, username, department, role, active);
        }
    }
}
