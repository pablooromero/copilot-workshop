package com.accenture.aria.controller;

import com.accenture.aria.dto.PersonRequestDTO;
import com.accenture.aria.dto.PersonResponseDTO;
import com.accenture.aria.model.Person;
import com.accenture.aria.service.PersonMapper;
import com.accenture.aria.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public List<PersonResponseDTO> listAll(@RequestParam(required = false) Boolean activeOnly) {
        List<Person> persons = Boolean.TRUE.equals(activeOnly) ? service.findActive() : service.findAll();
        return persons.stream()
                .map(PersonMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(PersonMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PersonResponseDTO> getByEmail(@PathVariable String email) {
        return service.findByEmail(email)
                .map(PersonMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<PersonResponseDTO> getByUsername(@PathVariable String username) {
        return service.findByUsername(username)
                .map(PersonMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{department}")
    public List<PersonResponseDTO> getByDepartment(@PathVariable String department) {
        return service.findByDepartment(department).stream()
                .map(PersonMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<PersonResponseDTO> create(@Valid @RequestBody PersonRequestDTO dto) {
        Person toCreate = PersonMapper.toEntity(dto);
        Person created = service.create(toCreate);
        PersonResponseDTO resp = PersonMapper.toResponse(created);
        return ResponseEntity.created(URI.create("/api/persons/" + created.getId())).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PersonRequestDTO dto) {
        Person updatedEntity = PersonMapper.toEntity(dto);
        return service.update(id, updatedEntity)
                .map(PersonMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<PersonResponseDTO> deactivate(@PathVariable Long id) {
        return service.deactivate(id)
                .map(PersonMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
