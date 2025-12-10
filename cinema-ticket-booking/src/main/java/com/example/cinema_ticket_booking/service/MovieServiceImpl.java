package com.example.cinema_ticket_booking.service;

import com.example.cinema_ticket_booking.model.Movie;
import com.example.cinema_ticket_booking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie update(Long id, Movie movie) {
        movie.setId(id);
        int rowsAffected = movieRepository.update(movie);
        if (rowsAffected == 0) {
            throw new RuntimeException("Movie with id " + id + " not found");
        }
        return movie;
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public List<Movie> findByDuration(int minMinutes){
        return movieRepository.findByDuration(minMinutes);
    }
}
