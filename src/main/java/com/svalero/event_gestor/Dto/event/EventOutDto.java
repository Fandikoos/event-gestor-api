package com.svalero.event_gestor.Dto.event;

import com.svalero.event_gestor.Dto.rating.RatingOutDto;
import com.svalero.event_gestor.Dto.registration.RegistrationOutDto;
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
    private String address;
    private float lat;
    private float lng;
    private String description;
    private String category;  //Carrera, Torneo, Partido, Futbol...
    private int participants;
    private String price;
    private String eventImage;
    private List<RegistrationOutDto> registrations;
    private List<RatingOutDto> ratings;
    private Long adminId;
}
