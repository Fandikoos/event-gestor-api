package com.svalero.event_gestor.controller;

import com.svalero.event_gestor.Domain.Event;
import com.svalero.event_gestor.Dto.EventInDto;
import com.svalero.event_gestor.Dto.EventOutDto;
import com.svalero.event_gestor.Service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
//Para que las request tengan ya el /events, ahorro de codigo
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAll() {
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EventOutDto> addEvent(@Valid @RequestBody EventInDto event) throws IOException {
        EventOutDto newEvent = eventService.addEvent(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }
}
