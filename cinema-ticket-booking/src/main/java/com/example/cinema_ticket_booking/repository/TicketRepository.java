package com.example.cinema_ticket_booking.repository;

import com.example.cinema_ticket_booking.model.Ticket;

public interface TicketRepository {
    void save(Ticket ticket);
}
