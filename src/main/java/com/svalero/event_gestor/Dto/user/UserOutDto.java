package com.svalero.event_gestor.Dto.user;

import com.svalero.event_gestor.Dto.rating.RatingOutDto;
import com.svalero.event_gestor.Dto.registration.RegistrationOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {

    private long id;
    private String name;
    private String email;
    private String phone;
    private List<RegistrationOutDto> registrations;
    private List<RatingOutDto> ratings;

}
