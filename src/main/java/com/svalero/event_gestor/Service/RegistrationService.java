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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    private DateTimeFormatter formatterDateRegistration = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public List<RegistrationOutDto> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();

        return registrations.stream()
                .map(registration -> modelMapper.map(registration, RegistrationOutDto.class))
                .collect(Collectors.toList());
    }

    public List<RegistrationOutDto> findEventsByUserId(long userId){
        List<Registration> registrations = registrationRepository.findRegistrationsByUserId(userId);

        return registrations.stream()
                .map(registration -> modelMapper.map(registration, RegistrationOutDto.class))
                .collect(Collectors.toList());
    }

    // Agregar una nueva inscripción
    public RegistrationOutDto addRegistration(RegistrationInDto registrationInDto) {
        Registration registration = modelMapper.map(registrationInDto, Registration.class);

        registration.setRegistrationDate(LocalDateTime.now());

        Registration newRegistration = registrationRepository.save(registration);
        return modelMapper.map(newRegistration, RegistrationOutDto.class);
    }

    // Eliminar una inscripción
    public void removeRegistration(long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        registrationRepository.delete(registration);
    }
}
