package com.svalero.event_gestor.controller;

import com.svalero.event_gestor.Domain.Registration;
import com.svalero.event_gestor.Dto.registration.RegistrationInDto;
import com.svalero.event_gestor.Dto.registration.RegistrationOutDto;
import com.svalero.event_gestor.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    // Obtener todas las inscripciones
    @GetMapping
    public ResponseEntity<List<RegistrationOutDto>> getAllRegistrations() {
        return new ResponseEntity<>(registrationService.getAllRegistrations(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RegistrationOutDto>> findEventById(@PathVariable long userId){
        return new ResponseEntity<>(registrationService.findEventsByUserId((userId)), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isUserRegistered(@RequestParam long eventId,@RequestParam long userId) {
        boolean isRegistered = registrationService.isUserRegisteredForEvent(eventId, userId);
        return ResponseEntity.ok(isRegistered);
    }

    // Agregar una nueva inscripción
    @PostMapping
    public ResponseEntity<RegistrationOutDto> addRegistration(@RequestBody RegistrationInDto registrationInDto) {
        RegistrationOutDto newRegistration = registrationService.addRegistration(registrationInDto);
        return new ResponseEntity<>(newRegistration, HttpStatus.CREATED);
    }

    // Eliminar una inscripción
    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> removeRegistration(@PathVariable long registrationId) {
        registrationService.removeRegistration(registrationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
