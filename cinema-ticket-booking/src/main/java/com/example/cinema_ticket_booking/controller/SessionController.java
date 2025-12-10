package com.example.cinema_ticket_booking.controller;

import com.example.cinema_ticket_booking.model.Session;
import com.example.cinema_ticket_booking.service.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.Patch;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/sessions")
public class SessionController implements SessionControllerApi {

    private final SessionService sessionService;
    private final ObjectMapper objectMapper;

    @Autowired
    public SessionController(SessionService sessionService, ObjectMapper objectMapper){
        this.sessionService = sessionService;
        this.objectMapper = objectMapper;
    }

    @Override
    @GetMapping
    public List<Session> getAll(
    ){
        return sessionService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Session> findById(@PathVariable Long id){
        return ResponseEntity.of(sessionService.findById(id));
    }

    @GetMapping("/search")
    public List<Session> findByMovieId(
            @RequestParam Long id
    ) {
        return sessionService.findByMovieId(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Session> save(@PathVariable Long id, @RequestBody @Valid Session sessionDto){
        if(sessionDto.getId() != null && !sessionDto.getId().equals(id)){
            return ResponseEntity.badRequest().build();
        }
        Session session = new Session();
        session.setId(id);
        session.setMovieId(sessionDto.getMovieId());
        session.setHallNumber(sessionDto.getHallNumber());
        session.setDateTime(sessionDto.getDateTime());
        session.setPrice(sessionDto.getPrice());

        session = sessionService.save(session);
        return ResponseEntity.ok(session);
    }

    @Override
    @PostMapping
    public ResponseEntity<Session> create(@RequestBody @Valid Session sessionDto){
        if(sessionDto.getId() != null){
            return ResponseEntity.badRequest().build();
        }

        Session session = sessionService.save(sessionDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(session.getId()).toUri();
        return ResponseEntity.created(uri).body(session);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        try {
            sessionService.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There are tickets for this session.");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found.");
        }
    }

    // RFC-6902 JSON Patch
    @Override
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Session> jsonPatch(@PathVariable Long id, @RequestBody JsonPatch patch){
        return patch(id, patch);
    }

    // RFC-7386 JSON Merge Patch
    @Override
    @PatchMapping(path = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Session> jsonMergePatch(@PathVariable Long id, @RequestBody JsonMergePatch patch){
        return patch(id, patch);
    }

    public ResponseEntity<Session> patch(Long id, Patch patch){
        Optional<Session> optionalSession = sessionService.findById(id);
        if(optionalSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        try{
            Session session = optionalSession.get();
            JsonNode json = objectMapper.convertValue(session, JsonNode.class);
            json = patch.apply(json);
            session = objectMapper.treeToValue(json, Session.class);
            session = sessionService.save(session);
            Session updatedSession = sessionService.findById(session.getId()).orElse(session);
            return ResponseEntity.ok(updatedSession);
        } catch(JsonPatchException | JsonProcessingException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
