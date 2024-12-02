package com.svalero.event_gestor.Service;


import com.svalero.event_gestor.Domain.Admin;
import com.svalero.event_gestor.Domain.Event;
import com.svalero.event_gestor.Dto.event.EventInDto;
import com.svalero.event_gestor.Dto.event.EventOutDto;
import com.svalero.event_gestor.Dto.rating.RatingOutDto;
import com.svalero.event_gestor.Dto.registration.RegistrationOutDto;
import com.svalero.event_gestor.Repository.AdminRepository;
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
    private AdminRepository adminRepository;

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
            dto.setAddress(event.getAddress());
            dto.setLat(event.getLat());
            dto.setLng(event.getLng());
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

    // Método para obtener múltiples eventos por sus IDs
    public List<Event> findEventsByIds(List<Long> eventIds) {
        return eventIds.stream()
                .map(this::findEventById)
                .collect(Collectors.toList());
    }

    public List<Event> findEventByCategory(String category){
        return  eventRepository.findByCategory(category);
    }

    public EventOutDto addEvent(EventInDto eventInDto, Long adminId) throws IOException {
        Event event = new Event();

        // Mapeamos los campos de EventInDto al objeto Event
        modelMapper.map(eventInDto, event);

        // Verificamos que el administrador existe antes de asociarlo
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin no encontrado"));

        // Asignamos el admin al evento
        event.setAdmin(admin);

        // Verificamos si hay una imagen en el EventInDto
        if (eventInDto.getEventImage() != null && !eventInDto.getEventImage().isEmpty()) {
            event.setEventImage(eventInDto.getEventImage().getBytes()); // Convertir a array de bytes
        }

        // Actualizamos la lista de eventos del administrador
        if (admin.getEvents() == null) {
            admin.setEvents(new ArrayList<>());  // Si no existe la lista, la inicializamos
        }
        admin.getEvents().add(event);  // Agregamos el nuevo evento a la lista de eventos del admin

        // Guardamos el evento en la base de datos
        Event newEvent = eventRepository.save(event);

        // También guardamos el admin para actualizar la relación bidireccional
        adminRepository.save(admin);

        // Convertimos el evento creado a un DTO de salida (EventOutDto)
        EventOutDto eventOutDto = new EventOutDto();
        modelMapper.map(newEvent, eventOutDto);

        // Devolvemos el EventOutDto para que el cliente lo vea
        return eventOutDto;
    }



    // Método para buscar eventos por adminId
    public List<Event> findByAdminId(Long adminId) {
        return eventRepository.findByAdminId(adminId);
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
        existingEvent.setAddress(eventInDto.getAddress());
        existingEvent.setLat(eventInDto.getLat());
        existingEvent.setLng(eventInDto.getLng());
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
