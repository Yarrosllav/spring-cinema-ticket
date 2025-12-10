package com.example.cinema_ticket_booking.service;

import org.springframework.transaction.annotation.Transactional;

public interface TicketService {
    @Transactional
    void bookTicket(Long sessionId, int seatNumber, String guestName);
}
