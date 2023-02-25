package com.elmc.booking.adapters.outgoing.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Set;

@Entity
@Table(name = "ROOM")
@Getter
public class RoomEntity {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Set<ScreeningEntity> screenings;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Min(1)
    private int rowsNumber;

    @Min(1)
    private int seatsInRowNumber;
}
