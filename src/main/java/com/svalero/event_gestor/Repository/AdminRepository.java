package com.svalero.event_gestor.Repository;

import com.svalero.event_gestor.Domain.Admin;
import com.svalero.event_gestor.Domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    List<Admin> findAll();

    Admin findByNameAndPassword(String name, String password);


}
