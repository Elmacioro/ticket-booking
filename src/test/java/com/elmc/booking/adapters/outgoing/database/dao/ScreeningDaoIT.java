package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.domain.screening.Screening;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ScreeningDaoIT {

    private static final String MOVIE_TITLE = "Pulp Fiction";

    @Autowired
    private ScreeningDao screeningDao;

    @Test
    @Sql("/data/simpleScreeningWithReservations.sql")
    public void findScreeningByIdShouldReturnRightScreening() {
        long screeningId = 1;

        Optional<Screening> optionalScreening = screeningDao.findScreeningById(screeningId);

        assertAll(() -> {
            assertTrue(optionalScreening.isPresent());
            Screening screening = optionalScreening.get();
            assertEquals(6, screening.getBookedSeats().size());
        });
    }

    @Test
    @Sql("/data/simpleScreeningsWithoutReservations.sql")
    public void getMovieScreeningsInDateRangeShouldReturnRightScreenings() {
        LocalDateTime start = LocalDateTime.parse("2023-04-05T14:20:00");
        LocalDateTime end = LocalDateTime.parse("2023-04-05T17:00:00");

        List<Screening> screenings = screeningDao.getMovieScreeningsInDateRange(start, end);

        assertEquals(2, screenings.size());
        screenings.forEach(screening ->
                assertEquals(MOVIE_TITLE, screening.getMovie().title()));
    }

}