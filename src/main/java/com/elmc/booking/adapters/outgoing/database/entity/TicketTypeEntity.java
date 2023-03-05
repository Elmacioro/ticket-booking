package com.elmc.booking.adapters.outgoing.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "TICKET_TYPE")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketType")
    private Set<TicketEntity> tickets;

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max = 25)
    private String name;

    @NotNull
    @Digits(integer = 9, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull
    @Size(min = 3, max = 3)
    private String currency;

}
