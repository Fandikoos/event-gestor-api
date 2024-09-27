package com.svalero.event_gestor.Utils;

import com.svalero.event_gestor.Domain.Event;
import com.svalero.event_gestor.Dto.EventOutDto;

import java.util.Base64;

public class EventMapper {

    // Método para convertir un objeto de tipo event a eventOutDto, sobre todo para el tema de la imagen
    public static EventOutDto mapToEventOutDto(Event event) {
        EventOutDto eventOutDto = new EventOutDto();
        eventOutDto.setId(event.getId());
        eventOutDto.setName(event.getName());
        eventOutDto.setDate(event.getDate());
        eventOutDto.setPlace(event.getPlace());
        eventOutDto.setDescription(event.getDescription());
        eventOutDto.setType(event.getType());
        eventOutDto.setStatus(event.getStatus());
        eventOutDto.setParticipants(event.getParticipants());
        eventOutDto.setPrice(event.getPrice());

        // Convertimos la imagen byte[] a base64 para poder mostrarla en el web
        if (event.getEventImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(event.getEventImage());
            eventOutDto.setEventImage(base64Image);
        }

        return eventOutDto;
    }
}
