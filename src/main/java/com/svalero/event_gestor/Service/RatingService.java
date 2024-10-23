package com.svalero.event_gestor.Service;

import com.svalero.event_gestor.Domain.Event;
import com.svalero.event_gestor.Domain.Rating;
import com.svalero.event_gestor.Domain.User;
import com.svalero.event_gestor.Dto.rating.RatingInDto;
import com.svalero.event_gestor.Dto.rating.RatingOutDto;
import com.svalero.event_gestor.Repository.EventRepository;
import com.svalero.event_gestor.Repository.RatingRepository;
import com.svalero.event_gestor.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Rating> getAllRatings(){
        return ratingRepository.findAll();
    }

    public RatingOutDto addRating(RatingInDto ratingInDto){
        Rating rating = new Rating();
        modelMapper.map(ratingInDto, rating);
        rating.setAverageRating((rating.getCustomerService() + rating.getEventQuality() + rating.getOrganizationSpeed() + rating.getValueForMoney()) / 4 );
        Rating newRating = ratingRepository.save(rating);

        RatingOutDto ratingOutDto = modelMapper.map(newRating, RatingOutDto.class);
        ratingOutDto.setUserId(newRating.getUser().getId());
        ratingOutDto.setEventId(newRating.getEvent().getId());

        return ratingOutDto;
    }

    public void removeRating(long ratingId){
        Rating rating = ratingRepository.findById(ratingId).orElseThrow();
        ratingRepository.delete(rating);
    }

}
