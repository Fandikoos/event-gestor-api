package com.svalero.event_gestor.controller;

import com.svalero.event_gestor.Domain.Registration;
import com.svalero.event_gestor.Dto.registration.RegistrationInDto;
import com.svalero.event_gestor.Dto.registration.RegistrationOutDto;
import com.svalero.event_gestor.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    // Obtener todas las inscripciones
    @GetMapping
    public ResponseEntity<List<RegistrationOutDto>> getAllRegistrations() {
        return new ResponseEntity<>(registrationService.getAllRegistrations(), HttpStatus.OK);
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
