package com.example.cinema_ticket_booking.model;

import lombok.Data;

@Data
public class Seat {
    private int raw;
    private int number;
    private boolean isAvailable = true;
}
