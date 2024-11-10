package com.svalero.event_gestor.Repository;

import com.svalero.event_gestor.Domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findAll();

    List<Event> findByCategory(String category);

}
