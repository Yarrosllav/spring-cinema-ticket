package com.example.cinema_ticket_booking.controller;

import com.example.cinema_ticket_booking.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SessionService sessionService;

    @Autowired
    public SeatController(SessionService sessionService){
        this.sessionService = sessionService;
    }


}
