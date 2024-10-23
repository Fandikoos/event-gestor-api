package com.svalero.event_gestor.Dto.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingOutDto {

    private long id;

    private int organizationSpeed;

    private int eventQuality;

    private int customerService;

    private int valueForMoney;

    private double averageRating;

    private Long eventId;

    private Long userId;
}
