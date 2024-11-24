package com.svalero.event_gestor.controller;

import com.svalero.event_gestor.Domain.Admin;
import com.svalero.event_gestor.Domain.User;
import com.svalero.event_gestor.Dto.admin.AdminInDto;
import com.svalero.event_gestor.Dto.admin.AdminOutDto;
import com.svalero.event_gestor.Dto.user.UserInDto;
import com.svalero.event_gestor.Dto.user.UserOutDto;
import com.svalero.event_gestor.Service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<AdminOutDto>> getAllAdmins(){
        return new ResponseEntity<>(adminService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Admin> getUserByNameAndPassword(@RequestParam String name, @RequestParam String password) {
        return adminService.findByNameAndPassword(name, password)
                .map(admin -> new ResponseEntity<>(admin, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AdminOutDto> addAdmin(@Valid @RequestBody AdminInDto admin) throws IOException {
        AdminOutDto newAdmin = adminService.addAdmin(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{adminId}")
    public ResponseEntity<AdminOutDto> modifyAdmin(@PathVariable long adminId, @Valid @RequestBody AdminInDto admin) throws IOException{
        AdminOutDto modifyAdmin = adminService.modifyAdmin(admin, adminId);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
