package com.example.cinema_ticket_booking.repository;

import com.example.cinema_ticket_booking.model.Session;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    List<Session> findAll();
    Optional<Session> findById(Long id);
    Session save(Session session);
    void deleteById(Long id);
}
