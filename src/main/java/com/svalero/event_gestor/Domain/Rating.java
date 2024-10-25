package com.svalero.event_gestor.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int organizationSpeed;

    @Column
    private int eventQuality;

    @Column
    private int customerService;

    @Column
    private int valueForMoney;

    @Column
    private double averageRating;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}
