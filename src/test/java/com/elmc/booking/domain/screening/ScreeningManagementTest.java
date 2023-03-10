package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.ports.dto.outgoing.MovieScreeningsDto;
import com.elmc.booking.domain.ports.dto.outgoing.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.dto.shared.ScreeningTimeDto;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import com.elmc.booking.domain.screening.exceptions.TooLongIntervalException;
import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningManagementTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private ScreeningManagement screeningManagement;

    @Test
    void searchForMovieScreeningsShouldThrowExceptionWhenInvalidDateRange() {
        LocalDateTime endTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime startTime = endTime.plusDays(5);

        assertThrows(InvalidScreeningTimeIntervalException.class,
                () -> screeningManagement.searchForMovieScreenings(startTime, endTime));
    }

    @Test
    void searchForMovieScreeningsShouldThrowExceptionWhenIntervalTooLong() {
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime endTime = startTime.plusWeeks(2);

        assertThrows(TooLongIntervalException.class,
                () -> screeningManagement.searchForMovieScreenings(startTime, endTime));
    }

    @Test
    void searchForMovieScreeningsShouldReturnCorrectlySortedData() {
        List<Screening> unsortedScreenings = prepareUnsortedScreenings();
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime endTime = startTime.plusDays(3);

        when(screeningRepository.getMovieScreeningsInDateRange(startTime, endTime))
                .thenReturn(unsortedScreenings);

        List<MovieScreeningsDto> movieScreeningsDtos = screeningManagement.searchForMovieScreenings(startTime, endTime);
        assertSortedByMovieTitle(movieScreeningsDtos);
        assertAllScreeningTimesSortedByStartTime(movieScreeningsDtos);
    }

    @Test
    void getScreeningDetailsShouldReturnRightDetailsWhenCorrectScreeningId() {
        Screening screening = prepareSingleScreening();
        ScreeningDetailsDto expected = new ScreeningDetailsDto(screening);

        when(screeningRepository.getScreeningById(screening.getId())).thenReturn(screening);

        ScreeningDetailsDto actual = screeningManagement.getScreeningDetails(screening.getId());
        assertEquals(expected, actual);
    }

    private void assertSortedByMovieTitle(List<MovieScreeningsDto> movieScreeningsDtos) {
        assertTrue(Ordering.from(Comparator.comparing(MovieScreeningsDto::getMovieTitle))
                .isOrdered(movieScreeningsDtos));
    }

    private void assertAllScreeningTimesSortedByStartTime(List<MovieScreeningsDto> movieScreeningsDtos) {
        movieScreeningsDtos.forEach(dto -> assertTrue(Ordering.from(Comparator.comparing(ScreeningTimeDto::start))
                .isOrdered(dto.getScreeningTimes())));
    }


    private List<Screening> prepareUnsortedScreenings() {
        Movie pulpFiction = prepareMovie(1, "PulpFiction");
        Movie django = prepareMovie(2, "Django");
        Movie hateful8 = prepareMovie(3, "Hateful 8");

        Room room = new Room("Room A", 1, 1);
        List<Seat> seats = prepareSeatsForRoom(room);

        return List.of(
                new Screening(UUID.randomUUID(), pulpFiction, room, LocalDateTime.parse("2023-01-01T12:30:00"), LocalDateTime.parse("2023-01-01T13:30:00"), seats),
                new Screening(UUID.randomUUID(), pulpFiction, room, LocalDateTime.parse("2023-01-01T11:30:00"), LocalDateTime.parse("2023-01-01T12:30:00"), seats),
                new Screening(UUID.randomUUID(), pulpFiction, room, LocalDateTime.parse("2023-01-01T14:30:00"), LocalDateTime.parse("2023-01-01T15:30:00"), seats),
                new Screening(UUID.randomUUID(), django, room, LocalDateTime.parse("2023-01-01T09:30:00"), LocalDateTime.parse("2023-01-01T10:30:00"), seats),
                new Screening(UUID.randomUUID(), django, room, LocalDateTime.parse("2023-01-01T07:30:00"), LocalDateTime.parse("2023-01-01T08:30:00"), seats),
                new Screening(UUID.randomUUID(), django, room, LocalDateTime.parse("2023-01-01T08:30:00"), LocalDateTime.parse("2023-01-01T09:30:00"), seats),
                new Screening(UUID.randomUUID(), hateful8, room, LocalDateTime.parse("2023-01-01T14:30:00"), LocalDateTime.parse("2023-01-01T15:30:00"), seats),
                new Screening(UUID.randomUUID(), hateful8, room, LocalDateTime.parse("2023-01-01T17:30:00"), LocalDateTime.parse("2023-01-01T18:30:00"), seats),
                new Screening(UUID.randomUUID(), hateful8, room, LocalDateTime.parse("2023-01-01T08:30:00"), LocalDateTime.parse("2023-01-01T09:30:00"), seats)
        );
    }

    private Movie prepareMovie(long id, String title) {
        return new Movie(id, title, "lorem ipsum");
    }

    private List<Seat> prepareSeatsForRoom(Room room) {
        List<Seat> roomSeats = new ArrayList<>();
        for(int row = 1; row <= room.rowsNumber(); row++) {
            for (int seatNr = 1; seatNr <= room.seatsInRowNumber(); seatNr++) {
                roomSeats.add(new Seat(new SeatId(row, seatNr), SeatStatus.AVAILABLE));
            }
        }
        return roomSeats;
    }

    private Screening prepareSingleScreening() {
        Movie movie = prepareMovie(1, "Django");
        Room room = new Room("Room A", 2, 2);
        List<Seat> seats = List.of(
                new Seat(new SeatId(1, 1), SeatStatus.BOOKED),
                new Seat(new SeatId(1, 2), SeatStatus.BOOKED),
                new Seat(new SeatId(2, 1), SeatStatus.AVAILABLE),
                new Seat(new SeatId(2, 2), SeatStatus.AVAILABLE));
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        return new Screening(UUID.randomUUID(), movie, room, startTime, endTime, seats);
    }

}
