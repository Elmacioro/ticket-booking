package com.elmc.booking.adapters.outgoing.database.dao.mapper;

import com.elmc.booking.adapters.outgoing.database.entity.ReservationEntity;
import com.elmc.booking.adapters.outgoing.database.entity.TicketEntity;
import com.elmc.booking.adapters.outgoing.database.entity.TicketTypeEntity;
import com.elmc.booking.domain.reservation.Price;
import com.elmc.booking.domain.reservation.Reservation;
import com.elmc.booking.domain.reservation.Ticket;
import com.elmc.booking.domain.reservation.TicketType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DomainToEntityMapperIT {

    @Autowired
    private DomainToEntityMapper domainToEntityMapper;

    @Test
    void mapShouldMapTicketTypeToTicketTypeEntityCorrectly() {
        TicketType ticketType = prepareTicketType();

        TicketTypeEntity ticketTypeEntity = domainToEntityMapper.map(ticketType);

        assertEquals(ticketType.id(), ticketTypeEntity.getId());
        assertEquals(ticketType.name(), ticketTypeEntity.getName());
        assertEquals(ticketType.price().currency(), ticketTypeEntity.getCurrency());
        assertEquals(0, ticketType.price().amount().compareTo(ticketTypeEntity.getPrice()));
    }

    @Test
    @Sql("/data/simpleScreeningWithReservations.sql")
    void mapShouldMapTicketToTicketEntityCorrectly() {
        TicketType ticketType = prepareTicketType();
        Ticket ticket = new Ticket(1, 1, ticketType);

        TicketEntity ticketEntity = domainToEntityMapper.map(ticket);

        assertEquals(ticket.rowNumber(), ticketEntity.getRowNumber());
        assertEquals(ticket.seatNumber(), ticketEntity.getSeatNumber());
        assertEquals(ticket.ticketType().id(), ticketEntity.getTicketType().getId());
    }

    @Test
    @Sql("/data/simpleScreeningWithReservations.sql")
    void mapShouldMapReservationToReservationEntityCorrectly() {
        List<Ticket> tickets = prepareTickets();

        Reservation reservation = new Reservation(
                UUID.fromString("d4ca0e71-ba14-4fcf-b449-e00bb5b2d91e"),
                tickets,
                "Łucja",
                "Łęcka-Wesoła"
        );

        ReservationEntity reservationEntity = domainToEntityMapper.map(reservation);

        assertEquals(reservation.getReservationId(), reservationEntity.getId());
        assertEquals(reservation.getScreeningId(), reservationEntity.getScreening().getId());
        assertEquals(tickets.size(), reservationEntity.getTickets().size());
        assertEquals(reservation.getFirstname(), reservationEntity.getFirstname());
        assertEquals(reservation.getSurname(), reservationEntity.getSurname());
        assertEquals(reservation.getExpirationDate(), reservationEntity.getExpirationDate());
        assertEquals(reservation.isPaid(), reservationEntity.isPaid());
    }

    private List<Ticket> prepareTickets() {
        TicketType ticketType = prepareTicketType();
        return List.of(
                new Ticket(2, 1, ticketType),
                new Ticket(2, 2, ticketType));
    }

    private TicketType prepareTicketType() {
        return new TicketType(1, "adult", new Price(new BigDecimal(25), "PLN"));
    }
}