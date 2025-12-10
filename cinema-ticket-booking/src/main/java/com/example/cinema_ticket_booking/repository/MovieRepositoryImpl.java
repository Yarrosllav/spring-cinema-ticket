package com.example.cinema_ticket_booking.repository;

import com.example.cinema_ticket_booking.model.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    private final JdbcTemplate jdbcTemplate;

    public MovieRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Movie> movieRowMapper = (rs, rowNum) -> new Movie(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getInt("duration_min"),
            rs.getString("description")
    );

    @Override
    public List<Movie> findAll() {
        return jdbcTemplate.query("SELECT * FROM movies", movieRowMapper);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM movies WHERE id = ?", movieRowMapper, id)
                .stream().findFirst();
    }

    @Override
    public Movie save(Movie movie) {
        String sql = "INSERT INTO movies (title, duration_min, description) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getDurationMin());
            ps.setString(3, movie.getDescription());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            movie.setId(keyHolder.getKey().longValue());
        }
        return movie;
    }

    @Override
    public int update(Movie movie) {
        String sql = "UPDATE movies SET title = ?, duration_min = ?, description = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                movie.getTitle(),
                movie.getDurationMin(),
                movie.getDescription(),
                movie.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM movies WHERE id = ?", id);
    }

    @Override
    public List<Movie> findByDuration(int minMinutes) {
        return jdbcTemplate.query("SELECT * FROM movies WHERE duration_min > ?", movieRowMapper, minMinutes);
    }
}