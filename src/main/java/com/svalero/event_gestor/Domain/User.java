package com.svalero.event_gestor.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String phone;

    // Relación One-to-Many con Registro
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Registration> registrations;

    // Relación One-to-Many con Rating
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Rating> ratings;
}
