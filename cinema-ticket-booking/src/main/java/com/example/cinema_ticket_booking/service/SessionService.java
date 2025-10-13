package com.example.cinema_ticket_booking.service;

import com.example.cinema_ticket_booking.model.Session;

import java.util.List;

public interface SessionService {

    List<Session> findAll();
    Session findById(Long id);
    void save(Session session);
    void deleteById(Long id);

}
