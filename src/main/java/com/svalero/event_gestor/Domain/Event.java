package com.svalero.event_gestor.Domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private String address;
    @Column
    private float lat;
    @Column
    private float lng;
    @Column
    private String description;
    @Column
    private String category;  //Carrera, Torneo, Partido, Futbol...
    @Column
    private int participants;
    @Column
    private String price;
    @Lob
    private byte[] eventImage;
    // Relación One-to-Many con Registro
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Registration> registrations;

    // Relación One-to-Many con Rating
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Rating> ratings;
}
