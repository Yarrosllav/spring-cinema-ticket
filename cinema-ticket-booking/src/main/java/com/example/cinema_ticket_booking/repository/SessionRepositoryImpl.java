package com.example.cinema_ticket_booking.repository;

import com.example.cinema_ticket_booking.model.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class SessionRepositoryImpl implements SessionRepository{

    private final TreeMap<Long, Session> sessions = new TreeMap<>();


    public SessionRepositoryImpl(){
        sessions.put(1L, new Session(1L, "Dune 2", LocalDateTime.now().plusDays(15), 4));
        sessions.put(2L, new Session(2L, "Madagascar", LocalDateTime.now().plusDays(12), 6));
        sessions.put(3L, new Session(3L, "Avatar", LocalDateTime.now().plusDays(4), 4));
        sessions.put(4L, new Session(4L, "Avengers", LocalDateTime.now().plusDays(7), 1));
        sessions.put(5L, new Session(5L, "The Silence of the Lambs", LocalDateTime.now().plusDays(5), 2));
        sessions.put(6L, new Session(6L, "F1", LocalDateTime.now().plusDays(2), 3));
    }

    @Override
    public List<Session> findAll(LocalDate date, int page, int size) {
        return sessions.values().stream()
                .filter(session -> date == null || session.getDate().toLocalDate().equals(date))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Session> findById(Long id) {
        return Optional.ofNullable(sessions.get(id));
    }

    @Override
    public Session save(Session session) {

        if(session.getId() == null){
            Long id = sessions.lastKey() + 1;
            session = new Session(id, session.getTitle(), session.getDate(), session.getHallNumber());
        }

        sessions.put(session.getId(), session);

        return session;
    }

    @Override
    public void deleteById(Long id) {
        sessions.remove(id);
    }

}

