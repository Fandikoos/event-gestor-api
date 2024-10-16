package com.svalero.event_gestor.Dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {

    private long id;
    private String name;
    private String email;
    private String phone;

}
