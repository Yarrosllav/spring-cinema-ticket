package com.example.cinema_ticket_booking.service;

import com.example.cinema_ticket_booking.model.Session;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    List<Session> findAll();

    Optional<Session> findById(Long id);

    Session save(Session session);

    void deleteById(Long id);

    List<Session> findByMovieId(Long movieId);
}
