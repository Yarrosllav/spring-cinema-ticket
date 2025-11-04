package com.example.cinema_ticket_booking.repository;

import com.example.cinema_ticket_booking.model.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SessionRepositoryImpl implements SessionRepository{

    private final Map<Long, Session> sessionStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public SessionRepositoryImpl(){
        save(new Session(null, "Dune 2",
                LocalDateTime.now().plusDays(15).plusHours(4), 4));
        save(new Session(null, "Madagascar",
                LocalDateTime.now().plusDays(7).plusHours(12), 6));
        save(new Session(null, "<script>alert('Some text idk')</script>",
                LocalDateTime.now().plusDays(1), 5));
    }

    @Override
    public List<Session> findAll() {
        return new ArrayList<>(sessionStore.values());
    }

    @Override
    public Optional<Session> findById(Long id) {
        return Optional.ofNullable(sessionStore.get(id));
    }

    @Override
    public Session save(Session session) {
        if(session.getId() == null){
            session.setId(idCounter.incrementAndGet());
        }
        sessionStore.put(session.getId(), session);

        return session;
    }

    @Override
    public void deleteById(Long id) {
        sessionStore.remove(id);
    }
}
