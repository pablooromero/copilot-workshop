package com.accenture.ticketmanager.service;

import com.accenture.ticketmanager.model.Priority;
import com.accenture.ticketmanager.model.Status;
import com.accenture.ticketmanager.model.Ticket;
import com.accenture.ticketmanager.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository repo;

    public TicketService(TicketRepository repo) {
        this.repo = repo;
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

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
