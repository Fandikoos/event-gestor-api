package com.svalero.event_gestor.Service;

import com.svalero.event_gestor.Domain.Admin;
import com.svalero.event_gestor.Domain.User;
import com.svalero.event_gestor.Dto.admin.AdminInDto;
import com.svalero.event_gestor.Dto.admin.AdminOutDto;
import com.svalero.event_gestor.Dto.user.UserInDto;
import com.svalero.event_gestor.Dto.user.UserOutDto;
import com.svalero.event_gestor.Repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRespository;

    @Autowired
    private ModelMapper modelMapper;

    public List<AdminOutDto> findAll() {
        // Recogemos los admins de la bbdd
        List<Admin> admins = adminRespository.findAll();

        // Convertimos cada uno de esos admins a AdminOutDto y los devolvemos en forma de lista
        return admins.stream()
                .map(admin -> modelMapper.map(admin, AdminOutDto.class))
                .collect(Collectors.toList());
    }

    // Encontrar admin por id
    public Admin findAdminById(long adminId) {
        return adminRespository.findById(adminId).orElseThrow();
    }

    public Optional<Admin> findByNameAndPassword(String name, String password) {
        return Optional.ofNullable(adminRespository.findByNameAndPassword(name, password));
    }

    public AdminOutDto addAdmin(AdminInDto adminInDto) {
        Admin admin = modelMapper.map(adminInDto, Admin.class);

        Admin newAdmin = adminRespository.save(admin);
        return modelMapper.map(newAdmin, AdminOutDto.class);
    }

    public AdminOutDto modifyAdmin(AdminInDto adminInDto, long adminId) {
        Admin existingAdmin = adminRespository.findById(adminId).orElseThrow(() -> new RuntimeException("User not found"));

        existingAdmin.setName(adminInDto.getName());
        existingAdmin.setEmail(adminInDto.getEmail());
        existingAdmin.setPassword(adminInDto.getPassword());
        existingAdmin.setPhone(adminInDto.getPhone());

        Admin updatedAdmin = adminRespository.save(existingAdmin);
        return modelMapper.map(updatedAdmin, AdminOutDto.class);
    }


}
