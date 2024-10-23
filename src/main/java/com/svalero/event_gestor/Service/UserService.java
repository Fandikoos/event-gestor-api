package com.svalero.event_gestor.Service;

import com.svalero.event_gestor.Domain.User;
import com.svalero.event_gestor.Dto.registration.RegistrationOutDto;
import com.svalero.event_gestor.Dto.user.UserInDto;
import com.svalero.event_gestor.Dto.user.UserOutDto;
import com.svalero.event_gestor.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserOutDto> findAll() {
        List<User> users = userRepository.findAll();

        // Mapear cada usuario a UserOutDto
        return users.stream().map(user -> {
            UserOutDto userDto = new UserOutDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setPhone(user.getPhone());

            // Mapear las inscripciones de cada usuario a RegistrationOutDto
            List<RegistrationOutDto> registrationDtos = user.getRegistrations().stream()
                    .map(registration -> new RegistrationOutDto(
                            registration.getId(),
                            registration.getEvent().getId(),   // Obtener el eventId
                            registration.getUser().getId(),    // Obtener el userId
                            registration.getRegistrationDate() // Fecha de inscripción
                    ))
                    .collect(Collectors.toList());

            userDto.setRegistrations(registrationDtos);

            return userDto;
        }).collect(Collectors.toList());
    }

    // Encontrar usuario por id
    public User findUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with ID: " + userId + " not found."));
    }

    // Agregar un nuevo usuario
    public UserOutDto addUser(UserInDto userInDto) {
        User user = modelMapper.map(userInDto, User.class);

        // Aquí podrías asignar un rol por defecto, por ejemplo:
        // user.setRole("CLIENT");

        User newUser = userRepository.save(user);
        return modelMapper.map(newUser, UserOutDto.class);
    }

    // Eliminar un usuario por ID
    public void removeUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with ID: " + userId + " not found."));
        userRepository.delete(user);
    }

    // Modificar un usuario
    public UserOutDto modifyUser(UserInDto userInDto, long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userInDto.getName());
        existingUser.setEmail(userInDto.getEmail());
        existingUser.setPassword(userInDto.getPassword());
        existingUser.setPhone(userInDto.getPhone());

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserOutDto.class);
    }
}
