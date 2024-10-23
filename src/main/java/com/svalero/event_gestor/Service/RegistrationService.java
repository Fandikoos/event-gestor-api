package com.svalero.event_gestor.Service;

import com.svalero.event_gestor.Domain.Event;
import com.svalero.event_gestor.Domain.Registration;
import com.svalero.event_gestor.Domain.User;
import com.svalero.event_gestor.Dto.registration.RegistrationInDto;
import com.svalero.event_gestor.Dto.registration.RegistrationOutDto;
import com.svalero.event_gestor.Repository.EventRepository;
import com.svalero.event_gestor.Repository.RegistrationRepository;
import com.svalero.event_gestor.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    private DateTimeFormatter formatterDateRegistration = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    // Obtener todas las inscripciones
    public List<RegistrationOutDto> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();

        // Mapear cada objeto Registration a RegistrationOutDto
        return registrations.stream().map(registration -> {
            RegistrationOutDto dto = new RegistrationOutDto();
            dto.setId(registration.getId());
            dto.setEventId(registration.getEvent().getId());  // Aquí obtenemos el id del evento
            dto.setUserId(registration.getUser().getId());    // Aquí obtenemos el id del usuario
            dto.setRegistrationDate(registration.getRegistrationDate());
            return dto;
        }).collect(Collectors.toList());
    }

    // Agregar una nueva inscripción
    public RegistrationOutDto addRegistration(RegistrationInDto registrationInDto) {
        Registration registration = new Registration();

        // Encontrar el evento y el usuario asociados por sus IDs
        Event event = eventRepository.findById(registrationInDto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(registrationInDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        registration.setEvent(event);
        registration.setUser(user);

        String formattedDate =LocalDateTime.now().format(formatterDateRegistration);
        registration.setRegistrationDate(LocalDateTime.parse(formattedDate, formatterDateRegistration));

        // Guardar la inscripción
        Registration newRegistration = registrationRepository.save(registration);

        RegistrationOutDto registrationOutDto = new RegistrationOutDto();
        registrationOutDto.setId(newRegistration.getId());
        registrationOutDto.setUserId(newRegistration.getUser().getId());
        registrationOutDto.setEventId(newRegistration.getEvent().getId());

        // Convertir a DTO de salida y devolverlo
        return registrationOutDto;
    }

    // Eliminar una inscripción
    public void removeRegistration(long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        registrationRepository.delete(registration);
    }
}
