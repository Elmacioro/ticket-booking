package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.adapters.outgoing.database.entity.ReservationEntity;
import com.elmc.booking.adapters.outgoing.database.entity.ScreeningEntity;
import com.elmc.booking.adapters.outgoing.database.entity.TicketEntity;
import com.elmc.booking.adapters.outgoing.database.entity.TicketTypeEntity;
import com.elmc.booking.adapters.outgoing.database.repository.ReservationJpaRepository;
import com.elmc.booking.domain.ports.outgoing.ReservationRepository;
import com.elmc.booking.domain.reservation.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
@AllArgsConstructor
public class ReservationDao implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;
    private final EntityManager entityManager;

    @Override
    public long save(Reservation reservation) {

        ScreeningEntity screeningEntity = entityManager.getReference(ScreeningEntity.class, reservation.getScreeningId());
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setScreening(screeningEntity);
        reservationEntity.setExpirationDate(reservation.getExpirationDate());
        reservationEntity.setSurname(reservation.getSurname());
        reservationEntity.setFirstname(reservation.getFirstname());
        reservationEntity.setPaid(reservation.isPaid());

        Set<TicketEntity> ticketEntities = reservation.getTickets()
                .stream()
                .map(ticket -> {
                    TicketEntity ticketEntity = new TicketEntity();
                    ticketEntity.setRowNumber(ticket.rowNumber());
                    ticketEntity.setSeatInRowNumber(ticket.seatInRowNumber());
                    TicketTypeEntity ticketTypeEntity = entityManager.getReference(TicketTypeEntity.class, ticket.ticketType().id());
                    ticketEntity.setTicketType(ticketTypeEntity);
                    ticketEntity.setReservation(reservationEntity);
                    return ticketEntity;
                })
                .collect(Collectors.toSet());

        reservationEntity.setTickets(ticketEntities);

        reservationJpaRepository.save(reservationEntity);

        return reservationEntity.getId();
    }
}
