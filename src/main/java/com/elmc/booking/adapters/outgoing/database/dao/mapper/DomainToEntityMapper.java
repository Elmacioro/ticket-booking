package com.elmc.booking.adapters.outgoing.database.dao.mapper;

import com.elmc.booking.adapters.outgoing.database.entity.ReservationEntity;
import com.elmc.booking.adapters.outgoing.database.entity.ScreeningEntity;
import com.elmc.booking.adapters.outgoing.database.entity.TicketEntity;
import com.elmc.booking.adapters.outgoing.database.entity.TicketTypeEntity;
import com.elmc.booking.domain.reservation.Reservation;
import com.elmc.booking.domain.reservation.Ticket;
import com.elmc.booking.domain.reservation.TicketType;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Transactional
@Mapper(componentModel = "spring")
public abstract class DomainToEntityMapper {

    // We cannot inject it in allArgsConstructor as generated implementing class has to use super noArgsConstructor
    // thus we inject it via setter
    private EntityManager entityManager;

    @Mapping(target = "id", source = "reservationId")
    public abstract ReservationEntity map(Reservation reservation);

    @Mapping(target = "price", source = "price.amount")
    public abstract TicketTypeEntity map(TicketType ticketType);

    public abstract TicketEntity map(Ticket ticket);

    public abstract Set<TicketEntity> map(Set<Ticket> tickets);

    @AfterMapping
    void setTicketTypeReference(@MappingTarget TicketEntity ticketEntity, Ticket ticket) {
        TicketTypeEntity ticketTypeEntity = entityManager.getReference(TicketTypeEntity.class, ticket.ticketType().id());
        ticketEntity.setTicketType(ticketTypeEntity);
    }

    @AfterMapping
    void setScreeningReference(@MappingTarget ReservationEntity reservationEntity, Reservation reservation) {
        ScreeningEntity screeningEntity = entityManager.getReference(ScreeningEntity.class, reservation.getScreeningId());
        reservationEntity.getTickets()
                .forEach(ticketEntity -> ticketEntity.setReservation(reservationEntity));
        reservationEntity.setScreening(screeningEntity);
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
