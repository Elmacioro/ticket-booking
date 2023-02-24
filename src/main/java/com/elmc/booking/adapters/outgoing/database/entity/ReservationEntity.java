package com.elmc.booking.adapters.outgoing.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESERVATION")
@Getter
public class ReservationEntity {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private ScreeningEntity screening;

    @NotNull
    @Size(min = 1)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation")
    private List<TicketEntity> tickets = new ArrayList<>();

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "^[A-Z][a-zżźćńółęąś]*$")
    private String firstname;

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "^[A-Z][a-zżźćńółęąś]*(-[A-Z][a-zżźćńółęąś]*)?$")
    private String surname;

    @NotNull
    private LocalDateTime expirationDate;

    private boolean isPaid = false;
}
