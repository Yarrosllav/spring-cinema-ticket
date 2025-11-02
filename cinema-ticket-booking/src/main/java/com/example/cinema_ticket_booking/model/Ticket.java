package com.example.cinema_ticket_booking.model;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Scope("prototype")
@Data
public class Ticket {

    private Long id;
    private Session session;
    private Seat seat;
    private LocalDateTime purchaseTime;

    public Ticket(){
        this.purchaseTime = LocalDateTime.now();
    }
}
