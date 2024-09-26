package com.svalero.event_gestor.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOutDto {

    private long id;
    private String name;
    private LocalDate date;
    private String place;
    private String description;
    private String type;  //Carrera, Torneo, Partido, Futbol...
    private String status;  //Cancelado, activo, Finalizado...
    private int participants;
}
