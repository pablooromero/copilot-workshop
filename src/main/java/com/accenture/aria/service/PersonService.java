package com.accenture.aria.service;

import com.accenture.aria.model.Person;
import com.accenture.aria.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public List<Person> findAll() {
        return repo.findAll();
    }

    public List<Person> findActive() {
        return repo.findByActiveTrue();
    }

    public Optional<Person> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<Person> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public Optional<Person> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public List<Person> findByDepartment(String department) {
        return repo.findByDepartment(department);
    }

    public Person create(Person person) {
        if (person.getActive() == null) {
            person.setActive(true);
        }
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        return repo.save(person);
    }

    public Optional<Person> update(Long id, Person updated) {
        return repo.findById(id).map(existing -> {
            if (updated.getFirstName() != null) existing.setFirstName(updated.getFirstName());
            if (updated.getLastName() != null) existing.setLastName(updated.getLastName());
            if (updated.getEmail() != null) existing.setEmail(updated.getEmail());
            if (updated.getUsername() != null) existing.setUsername(updated.getUsername());
            if (updated.getDepartment() != null) existing.setDepartment(updated.getDepartment());
            if (updated.getRole() != null) existing.setRole(updated.getRole());
            if (updated.getActive() != null) existing.setActive(updated.getActive());
            existing.setUpdatedAt(LocalDateTime.now());
            return repo.save(existing);
        });
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Optional<Person> deactivate(Long id) {
        return repo.findById(id).map(person -> {
            person.setActive(false);
            person.setUpdatedAt(LocalDateTime.now());
            return repo.save(person);
        });
    }
}
