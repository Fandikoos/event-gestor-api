package com.svalero.event_gestor.Repository;

import com.svalero.event_gestor.Domain.Registration;
import com.svalero.event_gestor.Dto.registration.RegistrationOutDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Long> {

    List<Registration> findAll();

    List<Registration> findRegistrationsByUserId(long userId);
}
