package com.example.cinema_ticket_booking.repository;

import com.example.cinema_ticket_booking.model.Ticket;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepositoryImpl implements TicketRepository {
    private final JdbcClient jdbcClient;

    public TicketRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void save(Ticket ticket) {
        String sql = "INSERT INTO tickets (session_id, seat_number, guest_name) VALUES (:sessionId, :seat, :guest)";
        jdbcClient.sql(sql)
                .param("sessionId", ticket.getSessionId())
                .param("seat", ticket.getSeatNumber())
                .param("guest", ticket.getGuestName())
                .update();
    }
}
