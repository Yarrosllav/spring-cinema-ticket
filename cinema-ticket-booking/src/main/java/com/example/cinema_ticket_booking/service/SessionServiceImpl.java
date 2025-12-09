package com.example.cinema_ticket_booking.service;

import com.example.cinema_ticket_booking.model.Session;
import com.example.cinema_ticket_booking.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService{

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository){
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> findAll(LocalDate date, int page, int size) {
        return sessionRepository.findAll(date, page, size);
    }

    @Override
    public Optional<Session> findById(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public void deleteById(Long id) {
        sessionRepository.deleteById(id);
    }
}
