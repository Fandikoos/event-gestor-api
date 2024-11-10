package com.svalero.event_gestor.Dto.event;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventInDto {

    @NotNull(message = "It is mandatory to establish the name of the event")
    private String name;
    @NotNull(message = "It is mandatory to establish the date of the event")
    private LocalDate date;
    @NotNull(message = "It is mandatory to establish the address of the event")
    private String address;
    @NotNull(message = "It is mandatory to establish the latitud of event")
    private float lat;
    @NotNull(message = "It is mandatory to establish the lingitud of the event")
    private float lng;
    private String description;
    private String category;
    @Min(value = 2, message = "Minimium participants must be 2")
    private int participants;
    private String price;
    private MultipartFile eventImage;

}
