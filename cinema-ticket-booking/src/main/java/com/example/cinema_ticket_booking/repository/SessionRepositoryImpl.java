package com.example.cinema_ticket_booking.repository;

import com.example.cinema_ticket_booking.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private final JdbcClient jdbcClient;

    @Autowired
    public SessionRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Session> findAll() {
        String sql = """
                SELECT s.id, s.movie_id, m.title as movie_title, s.hall_number, s.date_time, s.price 
                FROM sessions s 
                JOIN movies m ON s.movie_id = m.id
                """;

        return jdbcClient.sql(sql)
                .query(Session.class)
                .list();
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = """
                SELECT s.id, s.movie_id, m.title as movie_title, s.hall_number, s.date_time, s.price 
                FROM sessions s 
                JOIN movies m ON s.movie_id = m.id 
                WHERE s.id = :id
                """;

        return jdbcClient.sql(sql)
                .param("id", id)
                .query(Session.class)
                .optional();
    }

    @Override
    public Session save(Session session) {
        if (session.getId() == null) {
            return create(session);
        } else {
            return update(session);
        }
    }

    private Session create(Session session) {
        String sql = "INSERT INTO sessions (movie_id, hall_number, date_time, price) VALUES (:movieId, :hallNumber, :dateTime, :price)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .param("movieId", session.getMovieId())
                .param("hallNumber", session.getHallNumber())
                .param("dateTime", session.getDateTime())
                .param("price", session.getPrice())
                .update(keyHolder);

        if (keyHolder.getKey() != null) {
            session.setId(keyHolder.getKey().longValue());
        }
        return session;
    }

    private Session update(Session session) {
        String sql = """
                UPDATE sessions 
                SET movie_id = :movieId, 
                    hall_number = :hallNumber, 
                    date_time = :dateTime, 
                    price = :price 
                WHERE id = :id
                """;

        int rowsAffected = jdbcClient.sql(sql)
                .param("movieId", session.getMovieId())
                .param("hallNumber", session.getHallNumber())
                .param("dateTime", session.getDateTime())
                .param("price", session.getPrice())
                .param("id", session.getId())
                .update();

        if (rowsAffected == 0) {
            throw new RuntimeException("Session with id " + session.getId() + " not found");
        }

        return session;
    }

    @Override
    public void deleteById(Long id) {
        jdbcClient.sql("DELETE FROM sessions WHERE id = :id")
                .param("id", id)
                .update();
    }

    @Override
    public List<Session> findByMovieId(Long movieId) {
        String sql = """
                SELECT s.id, s.movie_id, m.title as movie_title, s.hall_number, s.date_time, s.price 
                FROM sessions s 
                JOIN movies m ON s.movie_id = m.id 
                WHERE s.movie_id = :movieId
                """;
        return jdbcClient.sql(sql)
                .param("movieId", movieId)
                .query(Session.class)
                .list();
    }
}

