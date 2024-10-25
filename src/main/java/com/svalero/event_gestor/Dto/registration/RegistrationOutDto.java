package com.svalero.event_gestor.Dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationOutDto {

    private long id;          // ID de la inscripción
    private long eventId;      // ID del evento asociado
    private long userId;       // ID del usuario asociado
    private LocalDateTime registrationDate;
}
