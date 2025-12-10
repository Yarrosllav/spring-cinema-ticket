package com.example.cinema_ticket_booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Long id;
    private Long sessionId;
    private Integer seatNumber;
    private String guestName;
    private LocalDateTime purchaseTime;
}

