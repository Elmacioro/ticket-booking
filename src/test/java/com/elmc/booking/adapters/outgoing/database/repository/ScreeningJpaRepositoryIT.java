package com.elmc.booking.adapters.outgoing.database.repository;

import com.elmc.booking.adapters.outgoing.database.entity.ScreeningEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ScreeningJpaRepositoryIT {

    private static final String MOVIE_TITLE = "Pulp Fiction";

    @Autowired
    private ScreeningJpaRepository screeningJpaRepository;

    @Test
    @Sql("/data/simpleScreeningsWithoutReservations.sql")
    public void findScreeningsInDateRangeShouldReturnCorrectEntities() {
        // It should fetch 2 screenings, both playing the same movie 'Pulp Fiction'
        LocalDateTime start = LocalDateTime.parse("2023-04-05T14:20:00");
        LocalDateTime end = LocalDateTime.parse("2023-04-05T17:00:00");

        List<ScreeningEntity> fetchedScreenings = screeningJpaRepository.findScreeningsInDateRange(start, end);

        assertEquals(2, fetchedScreenings.size());
        fetchedScreenings.forEach(screeningEntity ->
                assertEquals(MOVIE_TITLE, screeningEntity.getMovie().getTitle()));
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
        UUID wrongScreeningId = UUID.fromString("7edc3ef0-3247-4a00-bed5-67fec54480e5");

        Optional<ScreeningEntity> empty = screeningJpaRepository.findScreeningWithReservationsAndTicketsById(wrongScreeningId);

        assertTrue(empty.isEmpty());
    }

    @Test
    @Sql("/data/simpleScreeningWithReservations.sql")
    public void findScreeningWithReservationsAndTicketsByIdShouldReturnCorrectScreeningEntity() {
        UUID screeningId = UUID.fromString("d4ca0e71-ba14-4fcf-b449-e00bb5b2d91e");

        Optional<ScreeningEntity> screeningEntity = screeningJpaRepository.findScreeningWithReservationsAndTicketsById(screeningId);

        assertAll(()-> {
            assertTrue(screeningEntity.isPresent());
            ScreeningEntity entity = screeningEntity.get();

            assertEquals(3, entity.getReservations().size());
            assertEquals(MOVIE_TITLE, entity.getMovie().getTitle());
        });
    }

}
