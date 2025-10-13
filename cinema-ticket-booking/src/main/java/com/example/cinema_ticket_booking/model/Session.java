package com.example.cinema_ticket_booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Long id;
    private String title;
    private LocalDateTime date;
    private int hallNumber;

}
