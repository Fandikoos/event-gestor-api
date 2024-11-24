package com.svalero.event_gestor.Dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOutDto {

    private long id;
    private String name;
    private String email;
    private  String password;
    private String phone;
}
