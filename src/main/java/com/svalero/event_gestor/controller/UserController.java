package com.svalero.event_gestor.controller;

import com.svalero.event_gestor.Domain.User;
import com.svalero.event_gestor.Dto.user.UserInDto;
import com.svalero.event_gestor.Dto.user.UserOutDto;
import com.svalero.event_gestor.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserOutDto> addUser(@Valid @RequestBody UserInDto user) throws IOException{
        UserOutDto newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId){
        userService.removeUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserOutDto> modifyUser(@PathVariable long userId, @Valid @RequestBody UserInDto user) throws IOException{
        UserOutDto modifyUser = userService.modifyUser(user, userId);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
