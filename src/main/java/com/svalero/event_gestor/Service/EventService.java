package com.svalero.event_gestor.Service;


import com.svalero.event_gestor.Domain.Event;
import com.svalero.event_gestor.Dto.EventInDto;
import com.svalero.event_gestor.Dto.EventOutDto;
import com.svalero.event_gestor.Repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    public EventOutDto addEvent(EventInDto eventInDto) throws IOException {
        Event event = new Event();

        // Mapeamos los campos de AuthorInDto al objeto author, se los pasamos digamos, model mapper
        // se encarga de copiar estos valores
        // Tenemos el id automatico dentro del author por lo que lo coge de ahí que es incrementable, lo mismo pasaría con fechas...
        modelMapper.map(eventInDto, event);

        // Creamos un newAuthor que contiene los datos del InDto y lo guardamos en el repository
        // Guaradmos este objeto en la bbdd con el .save y el resultado se almacena en newAuthor
        Event newEvent = eventRepository.save(event);

        // Creamos un OutDto para devolver una respuesta, en el Postman por ejemplo
        EventOutDto eventOutDto = new EventOutDto();
        // Mapemaos los atributos del newAuthor en nuestro AuthorDto para la respuesta
        modelMapper.map(newEvent, eventOutDto);

        //Lo devolvemos para mostrarlo en Postman
        return eventOutDto;
    }
}
