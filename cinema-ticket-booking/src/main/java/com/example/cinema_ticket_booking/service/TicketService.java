package com.example.cinema_ticket_booking.service;

import com.example.cinema_ticket_booking.model.Seat;
import com.example.cinema_ticket_booking.model.Session;
import com.example.cinema_ticket_booking.model.Ticket;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class TicketService {

    private final ObjectProvider<Ticket> ticketProvider;
    private final AtomicLong ticketId = new AtomicLong(0);

    public TicketService(ObjectProvider<Ticket> ticketProvider){
        this.ticketProvider = ticketProvider;
    }

    public Ticket createTicket(Session session, Seat seat){
        Ticket ticket = ticketProvider.getObject();

        long newId = ticketId.incrementAndGet();
        ticket.setId(newId);

        ticket.setSession(session);
        ticket.setSeat(seat);

        return ticket;
    }
}
