package com.example.cinema_ticket_booking.controller;

import com.example.cinema_ticket_booking.model.Movie;
import com.example.cinema_ticket_booking.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController implements MovieControllerApi {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    @GetMapping
    public List<Movie> getAll() {
        return movieService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable Long id) {
        return ResponseEntity.of(movieService.findById(id));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody @Valid Movie movie) {
        return movieService.create(movie);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @RequestBody @Valid Movie movie) {
        try {
            return ResponseEntity.ok(movieService.update(id, movie));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            movieService.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to delete, some sessions have the movie");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The movie not found.");
        }
    }

    @Override
    @GetMapping("/search")
    public List<Movie> findByDuration(@RequestParam int min) {
        return movieService.findByDuration(min);
    }
}
