package com.example.cinema_ticket_booking.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Positive
    private Integer durationMin;

    private String description;
}
