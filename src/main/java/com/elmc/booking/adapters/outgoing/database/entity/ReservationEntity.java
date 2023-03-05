package com.elmc.booking.adapters.outgoing.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "RESERVATION")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    private UUID id;

    @NotNull
    @ManyToOne
    private ScreeningEntity screening;

    @NotNull
    @Size(min = 1)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation")
    private Set<TicketEntity> tickets;

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "^\\p{IsUppercase}\\p{IsLowercase}{2,}$")
    private String firstname;

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "^\\p{IsUppercase}\\p{IsLowercase}{2,}(-\\p{IsUppercase}\\p{IsLowercase}{2,})?$")
    private String surname;

    @NotNull
    private LocalDateTime expirationDate;

    private boolean isPaid = false;
}
