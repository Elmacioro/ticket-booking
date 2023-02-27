package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.adapters.outgoing.database.entity.ScreeningEntity;
import com.elmc.booking.adapters.outgoing.database.repository.ScreeningJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ScreeningJpaRepositoryIT {

    private static final String COMMON_MOVIE_TITLE = "Pulp Fiction";

    @Autowired
    private ScreeningJpaRepository screeningJpaRepository;

    @Test
    @Sql("/data/simpleScreeningsWithoutReservations.sql")
    public void findScreeningsInDateRangeTest() {
        // It should fetch 2 screenings, both playing the same movie 'Pulp Fiction'
        LocalDateTime start = LocalDateTime.parse("2023-04-05T14:20:00");
        LocalDateTime end = LocalDateTime.parse("2023-04-05T17:00:00");

        List<ScreeningEntity> fetchedScreenings = screeningJpaRepository.findScreeningsInDateRange(start, end);

        assertEquals(2, fetchedScreenings.size());
        fetchedScreenings.forEach(screeningEntity ->
                assertEquals(COMMON_MOVIE_TITLE, screeningEntity.getMovie().getTitle()));
    }

    @Test
    @Sql("/data/simpleScreeningsWithoutReservations.sql")
    public void findScreeningsInDateRangeTestShouldReturnEmptyListForInvalidDateRange() {
        LocalDateTime start = LocalDateTime.parse("2023-04-05T20:00:00");
        LocalDateTime end = LocalDateTime.parse("2023-04-05T10:00:00");

        List<ScreeningEntity> fetchedScreenings = screeningJpaRepository.findScreeningsInDateRange(start, end);

        assertTrue(fetchedScreenings.isEmpty());
    }

    @Test
    @Sql("/data/simpleScreeningWithReservations.sql")
    public void findScreeningWithReservationsAndTicketsByIdShouldReturnNullForWrongId() {
        long screeningId = 566;

        Optional<ScreeningEntity> empty = screeningJpaRepository.findScreeningWithReservationsAndTicketsById(screeningId);

        assertTrue(empty.isEmpty());
    }

    @Test
    @Sql("/data/simpleScreeningWithReservations.sql")
    public void findScreeningWithReservationsAndTicketsByIdTest() {
        long screeningId = 1;

        Optional<ScreeningEntity> screeningEntity = screeningJpaRepository.findScreeningWithReservationsAndTicketsById(screeningId);

        assertAll(()-> {
            assertTrue(screeningEntity.isPresent());
            ScreeningEntity entity = screeningEntity.get();

            assertEquals(3, entity.getReservations().size());
            assertEquals(COMMON_MOVIE_TITLE, entity.getMovie().getTitle());
        });
    }

}