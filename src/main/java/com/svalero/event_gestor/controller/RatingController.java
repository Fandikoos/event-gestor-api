package com.svalero.event_gestor.controller;

import com.svalero.event_gestor.Domain.Rating;
import com.svalero.event_gestor.Dto.rating.RatingInDto;
import com.svalero.event_gestor.Dto.rating.RatingOutDto;
import com.svalero.event_gestor.Service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings(){
        return new ResponseEntity<>(ratingService.getAllRatings(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RatingOutDto> saveRating(@Valid @RequestBody RatingInDto ratingInDto) {
        RatingOutDto newRating = ratingService.addRating(ratingInDto);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable long ratingId){
        ratingService.removeRating(ratingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
