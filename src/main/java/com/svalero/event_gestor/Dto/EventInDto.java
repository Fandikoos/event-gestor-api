package com.svalero.event_gestor.Dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventInDto {

    @NotNull(message = "It is mandatory to establish the name of the event")
    private String name;
    @NotNull(message = "It is mandatory to establish the date of the event")
    private LocalDate date;
    @NotNull(message = "It is mandatory to establish the place of the event")
    private String place;
    private String description;
    private String type;
    @NotNull(message = "It is mandatory to establish the status of the event")
    private String status;
    @Min(value = 2, message = "Minimium participants must be 2")
    private int participants;


}
