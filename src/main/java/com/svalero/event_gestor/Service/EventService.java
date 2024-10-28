package com.svalero.event_gestor.Service;


import com.svalero.event_gestor.Domain.Event;
import com.svalero.event_gestor.Dto.event.EventInDto;
import com.svalero.event_gestor.Dto.event.EventOutDto;
import com.svalero.event_gestor.Dto.rating.RatingOutDto;
import com.svalero.event_gestor.Dto.registration.RegistrationOutDto;
import com.svalero.event_gestor.Repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Método para obtener todos los eventos
    public List<EventOutDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        List<EventOutDto> eventOutDtos = new ArrayList<>();

        for (Event event : events) {
            EventOutDto dto = new EventOutDto();

            // Asignamos los valores básicos de los atributos
            dto.setId(event.getId());
            dto.setName(event.getName());
            dto.setDate(event.getDate());
            dto.setPlace(event.getPlace());
            dto.setDescription(event.getDescription());
            dto.setCategory(event.getCategory());
            dto.setParticipants(event.getParticipants());
            dto.setPrice(event.getPrice());

            // Conversión manual de la imagen de byte[] a base64
            if (event.getEventImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(event.getEventImage());
                dto.setEventImage(base64Image);
            } else {
                dto.setEventImage(null);  // En caso de que no haya imagen
            }

            // Conversión de los registros (Registration -> RegistrationOutDto)
            List<RegistrationOutDto> registrationDtos = event.getRegistrations().stream()
                    .map(registration -> {
                        RegistrationOutDto regDto = new RegistrationOutDto();
                        regDto.setId(registration.getId());
                        regDto.setEventId(registration.getEvent().getId());
                        regDto.setUserId(registration.getUser().getId());
                        regDto.setRegistrationDate(registration.getRegistrationDate());
                        return regDto;
                    })
                    .collect(Collectors.toList());
            dto.setRegistrations(registrationDtos);

            // Conversión de los ratings (Rating -> RatingOutDto)
            List<RatingOutDto> ratingDtos = event.getRatings().stream()
                    .map(rating -> {
                        RatingOutDto ratingDto = new RatingOutDto();
                        ratingDto.setId(rating.getId());
                        ratingDto.setOrganizationSpeed(rating.getOrganizationSpeed());
                        ratingDto.setEventQuality(rating.getEventQuality());
                        ratingDto.setCustomerService(rating.getCustomerService());
                        ratingDto.setValueForMoney(rating.getValueForMoney());
                        ratingDto.setAverageRating(rating.getAverageRating());
                        ratingDto.setEventId(rating.getEvent().getId());
                        ratingDto.setUserId(rating.getUser().getId());
                        return ratingDto;
                    })
                    .collect(Collectors.toList());
            dto.setRatings(ratingDtos);

            // Añadimos el DTO a la lista final
            eventOutDtos.add(dto);
        }

        return eventOutDtos;
    }


    public Event findEventById(long eventId){
        return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Evento con el id: " + eventId + " no encontrado."));
    }

    public EventOutDto addEvent(EventInDto eventInDto) throws IOException {
        Event event = new Event();

        // Mapeamos los campos de EventInDto al objeto Event, se los pasamos digamos, model mapper
        // se encarga de copiar estos valores
        // Tenemos el id automatico dentro del event por lo que lo coge de ahí que es incrementable, lo mismo pasaría con fechas...
        modelMapper.map(eventInDto, event);

        //Verificamos que existe la imagen
        if(eventInDto.getEventImage() != null){
            //La convertimos a un array de bytes
            event.setEventImage(eventInDto.getEventImage().getBytes());
        }

        // Creamos un newEvent que contiene los datos del InDto y lo guardamos en el repository
        // Guaradmos este objeto en la bbdd con el .save y el resultado se almacena en newEvent
        Event newEvent = eventRepository.save(event);

        // Creamos un OutDto para devolver una respuesta, en el Postman por ejemplo
        EventOutDto eventOutDto = new EventOutDto();
        // Mapemaos los atributos del newEvent en nuestro EventOutDto para la respuesta
        modelMapper.map(newEvent, eventOutDto);

        //Lo devolvemos para mostrarlo en Postman
        return eventOutDto;
    }

    public void removeEvent(long eventId){
        Event event = eventRepository.findById(eventId).orElseThrow();
        eventRepository.delete(event);
    }

    public EventOutDto modifyEvent(EventInDto eventInDto, long eventId) throws IOException {
        // Encuentramos el evento existente en la base de datos usando el ID proporcionado
        Event existingEvent = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        // Actualizamos los campos del evento existentes cn los valores proporcionados en el body del postman
        existingEvent.setName(eventInDto.getName());
        existingEvent.setDate(eventInDto.getDate());
        existingEvent.setPlace(eventInDto.getPlace());
        existingEvent.setDescription(eventInDto.getDescription());
        existingEvent.setParticipants(eventInDto.getParticipants());
        existingEvent.setPrice(eventInDto.getPrice());
        existingEvent.setCategory(eventInDto.getCategory());
        existingEvent.setEventImage(existingEvent.getEventImage());

        // Verificamos que hay una imagen nueva para actualizar la anterior
        if (eventInDto.getEventImage() != null && !eventInDto.getEventImage().isEmpty()){
            existingEvent.setEventImage(eventInDto.getEventImage().getBytes());
        }

        // Guardamos el evento
        Event updateEvent = eventRepository.save(existingEvent);

        // Mapea el evento actualizado al DTO de salida y lo devuelve en Postman
        return modelMapper.map(updateEvent, EventOutDto.class);
    }
}
