package com.svalero.event_gestor.Dto.admin;

import com.svalero.event_gestor.Domain.Event;
import com.svalero.event_gestor.Dto.event.EventOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOutDto {

    private long id;
    private String name;
    private String email;
    private  String password;
    private String phone;
    private List<EventOutDto> events;
}
