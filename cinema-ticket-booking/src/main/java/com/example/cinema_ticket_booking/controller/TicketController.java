package com.example.cinema_ticket_booking.controller;
import com.example.cinema_ticket_booking.model.TicketDto;
import com.example.cinema_ticket_booking.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController implements TicketControllerApi {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    @PostMapping
    public ResponseEntity<String> buyTicket(@RequestBody @Valid TicketDto ticket) {
        try {
            ticketService.bookTicket(ticket.getSessionId(), ticket.getSeat(), ticket.getName());
            return ResponseEntity.ok("Success: Ticket purchased");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error: the seat is unavailable");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
