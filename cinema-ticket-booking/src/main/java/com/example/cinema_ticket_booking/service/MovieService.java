package com.example.cinema_ticket_booking.service;

import com.example.cinema_ticket_booking.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll();

    Movie create(Movie movie);

    Movie update(Long id, Movie movie);

    void deleteById(Long id);

    Optional<Movie> findById(Long id);

    List<Movie> findByDuration(int minMinutes);
}
