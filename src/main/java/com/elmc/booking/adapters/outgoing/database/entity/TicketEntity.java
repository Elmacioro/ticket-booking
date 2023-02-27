package com.elmc.booking.adapters.outgoing.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TICKET")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
