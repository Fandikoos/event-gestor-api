package com.svalero.event_gestor.Service;

import com.svalero.event_gestor.Domain.User;
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

    //Obtener todos los usuarios
    public List<User> findAll(){
        return userRepository.findAll();
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
