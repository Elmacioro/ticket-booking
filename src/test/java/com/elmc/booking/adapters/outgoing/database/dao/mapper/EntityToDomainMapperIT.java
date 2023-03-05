package com.elmc.booking.adapters.outgoing.database.dao.mapper;

import com.elmc.booking.adapters.outgoing.database.entity.MovieEntity;
import com.elmc.booking.adapters.outgoing.database.entity.RoomEntity;
import com.elmc.booking.adapters.outgoing.database.entity.ScreeningEntity;
import com.elmc.booking.adapters.outgoing.database.entity.TicketTypeEntity;
import com.elmc.booking.domain.reservation.Price;
import com.elmc.booking.domain.reservation.TicketType;
import com.elmc.booking.domain.screening.Movie;
import com.elmc.booking.domain.screening.Room;
import com.elmc.booking.domain.screening.Screening;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class EntityToDomainMapperIT {

    @Autowired
    private EntityToDomainMapper entityToDomainMapper;

    @Autowired
    private EntityManager entityManager;

    @Test
    void mapShouldMapMovieEntityToMovieCorrectly() {
        MovieEntity movieEntity = new MovieEntity(
                1L,
                Collections.emptySet(),
                "Django",
                "SomeDesc"
        );

        Movie movie = entityToDomainMapper.map(movieEntity);

        assertEquals(movieEntity.getId(), movie.id());
        assertEquals(movieEntity.getTitle(), movie.title());
        assertEquals(movieEntity.getDescription(), movie.description());
    }

    @Test
    void mapShouldMapRoomEntityToRoomCorrectly() {
        RoomEntity roomEntity = new RoomEntity(
                1L,
                Collections.emptySet(),
                "Room A",
                5,
                5
        );

        Room room = entityToDomainMapper.map(roomEntity);

        assertEquals(roomEntity.getName(), room.name());
        assertEquals(roomEntity.getRowsNumber(), room.rowsNumber());
        assertEquals(roomEntity.getSeatsInRowNumber(), room.seatsInRowNumber());
    }

    @Test
    void mapTicketTypeEntityToPriceShouldMapCorrectly() {
        TicketTypeEntity ticketTypeEntity = prepareTicketType();

        Price price = entityToDomainMapper.mapTicketTypeEntityToPrice(ticketTypeEntity);

        assertEquals(ticketTypeEntity.getCurrency(), price.currency());
        assertEquals(0, ticketTypeEntity.getPrice().compareTo(price.amount()));
    }

    @Test
    void mapShouldMapTicketTypeEntityToTicketTypeCorrectly() {
        TicketTypeEntity ticketTypeEntity = prepareTicketType();

        TicketType ticketType = entityToDomainMapper.map(ticketTypeEntity);

        assertEquals(ticketTypeEntity.getId(), ticketType.id());
        assertEquals(ticketTypeEntity.getName(), ticketType.name());
        assertEquals(0, ticketTypeEntity.getPrice().compareTo(ticketType.price().amount()));
        assertEquals(ticketTypeEntity.getCurrency(), ticketType.price().currency());
    }

    @Test
    @Sql("/data/simpleScreeningWithReservations.sql")
    void mapShouldMapScreeningEntityToScreeningCorrectly() {
        ScreeningEntity screeningEntity = entityManager.getReference(ScreeningEntity.class,
                UUID.fromString("d4ca0e71-ba14-4fcf-b449-e00bb5b2d91e"));

        Screening screening = entityToDomainMapper.map(screeningEntity);

        assertEquals(screeningEntity.getId(), screening.getId());
        assertEquals(screeningEntity.getMovie().getTitle(), screening.getMovie().title());
        assertEquals(screeningEntity.getRoom().getName(), screening.getRoom().name());
        int screeningEntitySeatCount = screeningEntity.getRoom().getRowsNumber() * screening.getRoom().seatsInRowNumber();
        assertEquals(screeningEntitySeatCount, screening.getSeats().size());
        assertEquals(screeningEntity.getStartTime(), screening.getStartTime());
        assertEquals(screeningEntity.getEndTime(), screening.getEndTime());

    }

    private TicketTypeEntity prepareTicketType() {
        return new TicketTypeEntity(
                1L,
                new HashSet<>(),
                "adult",
                new BigDecimal(25),
                "PLN"
        );
    }
}
