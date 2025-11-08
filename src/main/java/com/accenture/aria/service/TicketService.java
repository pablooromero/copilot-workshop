package com.accenture.aria.service;

import com.accenture.aria.model.Person;
import com.accenture.aria.model.Priority;
import com.accenture.aria.model.Status;
import com.accenture.aria.model.Ticket;
import com.accenture.aria.repository.PersonRepository;
import com.accenture.aria.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository repo;
    private final PersonRepository personRepo;

    public TicketService(TicketRepository repo, PersonRepository personRepo) {
        this.repo = repo;
        this.personRepo = personRepo;
    }

    public List<Ticket> findAll() {
        return repo.findAll();
    }

    public Optional<Ticket> findById(Long id) {
        return repo.findById(id);
    }

    public Ticket create(Ticket ticket) {
        if (ticket.getStatus() == null) ticket.setStatus(Status.OPEN);
        if (ticket.getPriority() == null) ticket.setPriority(Priority.MEDIUM);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        return repo.save(ticket);
    }

    public Ticket createFromDTO(com.accenture.aria.dto.TicketRequestDTO dto) {
        Ticket ticket = TicketMapper.toEntity(dto);
        
        // Set reporter if provided
        if (dto.getReporterId() != null) {
            personRepo.findById(dto.getReporterId()).ifPresent(ticket::setReporter);
        }
        
        // Set assignee if provided
        if (dto.getAssigneeId() != null) {
            personRepo.findById(dto.getAssigneeId()).ifPresent(ticket::setAssignee);
        }
        
        return create(ticket);
    }

    public Optional<Ticket> update(Long id, Ticket updated) {
        return repo.findById(id).map(existing -> {
            if (updated.getTitle() != null) existing.setTitle(updated.getTitle());
            if (updated.getDescription() != null) existing.setDescription(updated.getDescription());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            if (updated.getPriority() != null) existing.setPriority(updated.getPriority());
            if (updated.getAssignee() != null) existing.setAssignee(updated.getAssignee());
            if (updated.getReporter() != null) existing.setReporter(updated.getReporter());
            existing.setUpdatedAt(LocalDateTime.now());
            return repo.save(existing);
        });
    }

    public Optional<Ticket> updateFromDTO(Long id, com.accenture.aria.dto.TicketRequestDTO dto) {
        return repo.findById(id).map(existing -> {
            if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
            if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
            if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
            if (dto.getPriority() != null) existing.setPriority(dto.getPriority());
            
            // Update reporter if provided
            if (dto.getReporterId() != null) {
                personRepo.findById(dto.getReporterId()).ifPresent(existing::setReporter);
            }
            
            // Update assignee if provided
            if (dto.getAssigneeId() != null) {
                personRepo.findById(dto.getAssigneeId()).ifPresent(existing::setAssignee);
            }
            
            existing.setUpdatedAt(LocalDateTime.now());
            return repo.save(existing);
        });
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
