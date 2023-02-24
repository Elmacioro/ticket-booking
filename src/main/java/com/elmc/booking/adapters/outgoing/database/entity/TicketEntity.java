package com.elmc.booking.adapters.outgoing.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "TICKET")
@Getter
public class TicketEntity {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private TicketTypeEntity ticketType;

    @NotNull
    @ManyToOne
    private ReservationEntity reservation;

    @Min(1)
    private int rowNumber;

    @Min(1)
    private int seatInRowNumber;


}
