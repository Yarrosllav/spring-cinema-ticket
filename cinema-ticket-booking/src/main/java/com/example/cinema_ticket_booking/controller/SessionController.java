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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
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
    public List<Session> sessions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ){
        return sessionService.findAll(date, page, size);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Session> findById(@PathVariable Long id){
        return ResponseEntity.of(sessionService.findById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Session> save(@PathVariable Long id, @RequestBody Session sessionDto){
        if(sessionDto.getId() != null && !sessionDto.getId().equals(id)){
            return ResponseEntity.badRequest().build();
        }
        Session session = new Session(id, sessionDto.getTitle(), sessionDto.getDate(), sessionDto.getHallNumber());
        session = sessionService.save(session);
        return ResponseEntity.ok(session);
    }

    @Override
    @PostMapping
    public ResponseEntity<Session> create(@RequestBody Session sessionDto){
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
    public void deleteByI(@PathVariable Long id){
        sessionService.deleteById(id);
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
            return ResponseEntity.ok(session);
        } catch(JsonPatchException | JsonProcessingException e){
            return ResponseEntity.badRequest().build();
        }
    }


}
