package com.svalero.event_gestor.Dto.event;

import com.svalero.event_gestor.Domain.Rating;
import com.svalero.event_gestor.Domain.Registration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOutDto {

    private long id;
    private String name;
    private LocalDate date;
    private String place;
    private String description;
    private String category;  //Carrera, Torneo, Partido, Futbol...
    private int participants;
    private String price;
    private String eventImage;
    private List<Registration> registrations;
    private List<Rating> ratings;
}
