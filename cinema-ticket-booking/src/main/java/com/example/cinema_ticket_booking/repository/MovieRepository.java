package com.example.cinema_ticket_booking.repository;

import com.example.cinema_ticket_booking.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> findAll();

    Optional<Movie> findById(Long id);

    Movie save(Movie movie);

    int update(Movie movie);

    void deleteById(Long id);

    List<Movie> findByDuration(int minMinutes);
}
