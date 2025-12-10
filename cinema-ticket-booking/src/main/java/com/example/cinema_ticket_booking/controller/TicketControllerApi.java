package com.example.cinema_ticket_booking.controller;

import com.example.cinema_ticket_booking.model.TicketDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Ticket Management", description = "API for purchasing tickets. Demonstrates database transactions.")
@RequestMapping("/tickets")
public interface TicketControllerApi {

    @Operation(
            summary = "Buy a ticket",
            description = "Creates a new ticket record. If the seat is already taken, the transaction is rolled back."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket purchased successfully"),
            @ApiResponse(responseCode = "400", description = "Seat is already booked or validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    ResponseEntity<String> buyTicket(
            @Parameter(description = "Ticket order details")
            @RequestBody @Valid TicketDto ticket
    );
}
