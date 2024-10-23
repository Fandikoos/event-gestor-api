package com.svalero.event_gestor.Repository;

import com.svalero.event_gestor.Domain.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Lo de RATING es que hace referencia a la entidad rating y que utiliza un Long como identificador
@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

    List<Rating> findAll();

}
