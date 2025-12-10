package com.example.cinema_ticket_booking.controller;

import com.example.cinema_ticket_booking.model.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Movies Management", description = "Movie management API (JdbcTemplate Demo)")
@RequestMapping("/movies")
public interface MovieControllerApi {

    @Operation(
            summary = "Get all movies",
            description = "Returns a list of all available movies."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class)))
    })
    @GetMapping
    List<Movie> getAll();

    @Operation(
            summary = "Get movie by ID",
            description = "Returns full details of a specific movie by its identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<Movie> getById(
            @Parameter(description = "Movie ID") @PathVariable Long id
    );

    @Operation(
            summary = "Create a movie",
            description = "Adds a new movie to the database. The ID is generated automatically."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "400", description = "Validation error (e.g., missing title)", content = @Content)
    })
    @PostMapping
        // Змінив return type на ResponseEntity, щоб можна було повернути статус 201
    Movie create(
            @Parameter(description = "Movie data") @RequestBody @Valid Movie movie
    );

    @Operation(
            summary = "Update movie",
            description = "Updates details of an existing movie."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
    })
    @PutMapping("/{id}")
    ResponseEntity<Movie> update(
            @Parameter(description = "Movie ID to update") @PathVariable Long id,
            @Parameter(description = "Updated movie data") @RequestBody @Valid Movie movie
    );

    @Operation(
            summary = "Delete movie",
            description = "Deletes a movie from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Cannot delete movie (e.g., sessions exist for this movie)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @Parameter(description = "Movie ID") @PathVariable Long id
    );

    @Operation(
            summary = "Find movies by duration",
            description = "Returns a list of movies longer than the specified value."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class)))
    })
    @GetMapping("/search")
    List<Movie> findByDuration(
            @Parameter(description = "Minimum duration in minutes")
            @RequestParam int min
    );
}
