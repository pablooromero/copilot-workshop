package com.accenture.aria.repository;

import com.accenture.aria.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
    Optional<Person> findByEmail(String email);
    
    Optional<Person> findByUsername(String username);
    
    List<Person> findByActiveTrue();
    
    List<Person> findByDepartment(String department);
}
