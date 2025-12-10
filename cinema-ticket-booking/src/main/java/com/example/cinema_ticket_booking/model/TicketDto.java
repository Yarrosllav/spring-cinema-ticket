package com.example.cinema_ticket_booking.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketDto {

    @NotNull
    private Long sessionId;

    @Min(value = 1)
    private int seat;

    @NotBlank
    private String name;
}
