package com.svalero.event_gestor.Domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;
    @Column
    private LocalDate date;
    @Column
    private String place;
    @Column
    private String description;
    @Column
    private String type;  //Carrera, Torneo, Partido, Futbol...
    @Column
    private String status;  //Cancelado, activo, Finalizado...
    @Column
    private int participants;
    @Column
    private String price;
    @Lob
    private byte[] eventImage;
}
