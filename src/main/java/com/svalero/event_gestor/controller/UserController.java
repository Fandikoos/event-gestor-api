package com.svalero.event_gestor.controller;

import com.svalero.event_gestor.Domain.User;
import com.svalero.event_gestor.Dto.user.UserInDto;
import com.svalero.event_gestor.Dto.user.UserOutDto;
import com.svalero.event_gestor.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserOutDto>> getAllUsers(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<User> getUserByNameAndPassword(@RequestParam String name, @RequestParam String password) {
        return userService.findByNameAndPassword(name, password)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/searchUser")
    public ResponseEntity<User> getUserById(@RequestParam long id){
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
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
