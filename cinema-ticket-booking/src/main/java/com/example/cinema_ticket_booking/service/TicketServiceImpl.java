package com.example.cinema_ticket_booking.service;

import com.example.cinema_ticket_booking.model.Ticket;
import com.example.cinema_ticket_booking.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public void bookTicket(Long sessionId, int seatNumber, String guestName) {
        Ticket ticket = new Ticket(null, sessionId, seatNumber, guestName, LocalDateTime.now());
        ticketRepository.save(ticket);
    }
}
