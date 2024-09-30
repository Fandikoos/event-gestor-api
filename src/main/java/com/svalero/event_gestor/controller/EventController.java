package com.svalero.event_gestor.controller;

import com.svalero.event_gestor.Domain.Event;
import com.svalero.event_gestor.Dto.EventInDto;
import com.svalero.event_gestor.Dto.EventOutDto;
import com.svalero.event_gestor.Service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<List<EventOutDto>> getAll() {
        return new ResponseEntity<>(eventService.getAllEvents(), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> findEventById(@PathVariable long eventId){
        Event event = eventService.findEventById(eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<EventOutDto> addEvent(@Valid @ModelAttribute EventInDto event) throws IOException {
        EventOutDto newEvent = eventService.addEvent(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> removeEvent(@PathVariable long eventId){
        eventService.removeEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{eventId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<EventOutDto> modifyEvent(@PathVariable long eventId, @Valid @ModelAttribute EventInDto event) throws IOException{
        EventOutDto updateEvent = eventService.modifyEvent(event, eventId);
        return new ResponseEntity<>(updateEvent, HttpStatus.OK);
    }
}
