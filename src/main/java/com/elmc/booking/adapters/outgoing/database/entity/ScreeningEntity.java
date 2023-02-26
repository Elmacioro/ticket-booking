package com.elmc.booking.adapters.outgoing.database.entity;

import com.elmc.booking.adapters.outgoing.database.validation.ValidLocalDateTimeRange;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "SCREENING")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ValidLocalDateTimeRange(start = "startTime", end = "endTime")
public class ScreeningEntity {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "screening")
    private Set<ReservationEntity> reservations;

    @NotNull
    @ManyToOne
    private MovieEntity movie;

    @NotNull
    @ManyToOne
    private RoomEntity room;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;
}
