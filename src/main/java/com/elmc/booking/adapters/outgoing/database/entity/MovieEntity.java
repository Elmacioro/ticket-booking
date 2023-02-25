package com.elmc.booking.adapters.outgoing.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Set;

@Entity
@Table(name = "MOVIE")
@Getter
public class MovieEntity {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private Set<ScreeningEntity> screenings;

    @NotNull
    @Size(min = 1, max = 250)
    private String title;

    @NotNull
    @Size(min = 1, max = 1500)
    private String description;

}
