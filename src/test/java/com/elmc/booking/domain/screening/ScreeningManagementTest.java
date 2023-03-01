package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.ports.dto.outgoing.MovieScreeningsDto;
import com.elmc.booking.domain.ports.dto.outgoing.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.dto.shared.ScreeningTimeDto;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import com.elmc.booking.domain.screening.exceptions.NoSuchScreeningException;
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
import java.util.Optional;

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
        LocalDateTime startTime = LocalDateTime.parse("2023-01-01T20:30:00");
        LocalDateTime endTime = LocalDateTime.parse("2023-01-01T15:00:00");

        assertThrows(InvalidScreeningTimeIntervalException.class,
                () -> screeningManagement.searchForMovieScreenings(startTime, endTime));
    }

    @Test
    void searchForMovieScreeningsShouldReturnCorrectlySortedData() {
        List<Screening> unsortedScreenings = prepareUnsortedScreenings();
        LocalDateTime startTime = LocalDateTime.parse("2023-01-01T00:00:00");
        LocalDateTime endTime = LocalDateTime.parse("2023-01-01T23:59:59");

        when(screeningRepository.getMovieScreeningsInDateRange(startTime, endTime))
                .thenReturn(unsortedScreenings);

        List<MovieScreeningsDto> movieScreeningsDtos = screeningManagement.searchForMovieScreenings(startTime, endTime);
        assertSortedByMovieTitle(movieScreeningsDtos);
        assertAllScreeningTimesSortedByStartTime(movieScreeningsDtos);
    }

    private void assertSortedByMovieTitle(List<MovieScreeningsDto> movieScreeningsDtos) {
        assertTrue(Ordering.from(Comparator.comparing(MovieScreeningsDto::getMovieTitle))
                .isOrdered(movieScreeningsDtos));
    }

    private void assertAllScreeningTimesSortedByStartTime(List<MovieScreeningsDto> movieScreeningsDtos) {
        movieScreeningsDtos.forEach(dto -> assertTrue(Ordering.from(Comparator.comparing(ScreeningTimeDto::start))
                .isOrdered(dto.getScreeningTimes())));
    }

    @Test
    void getScreeningDetailsShouldThrowExceptionWhenScreeningIdNotExist() {
        long wrongScreeningId = 5967;

        when(screeningRepository.findScreeningById(wrongScreeningId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchScreeningException.class,
                () -> screeningManagement.getScreeningDetails(wrongScreeningId));
    }

    @Test
    void getScreeningDetailsShouldReturnRightDetailsWhenCorrectScreeningId() {
        Screening screening = prepareSingleScreening();
        ScreeningDetailsDto expected = new ScreeningDetailsDto(screening);

        when(screeningRepository.findScreeningById(screening.getId()))
                .thenReturn(Optional.of(screening));

        ScreeningDetailsDto actual = screeningManagement.getScreeningDetails(screening.getId());
        assertEquals(expected, actual);
    }

    private List<Screening> prepareUnsortedScreenings() {
        Movie pulpFiction = prepareMovie(1, "PulpFiction");
        Movie django = prepareMovie(2, "Django");
        Movie hateful8 = prepareMovie(3, "Hateful 8");

        Room room = new Room("Room A", 1, 1);
        List<Seat> seats = prepareSeatsForRoom(room);

        return List.of(
                new Screening(1, pulpFiction, room, LocalDateTime.parse("2023-01-01T12:30:00"), LocalDateTime.parse("2023-01-01T13:30:00"), seats),
                new Screening(2, pulpFiction, room, LocalDateTime.parse("2023-01-01T11:30:00"), LocalDateTime.parse("2023-01-01T12:30:00"), seats),
                new Screening(3, pulpFiction, room, LocalDateTime.parse("2023-01-01T14:30:00"), LocalDateTime.parse("2023-01-01T15:30:00"), seats),
                new Screening(4, django, room, LocalDateTime.parse("2023-01-01T09:30:00"), LocalDateTime.parse("2023-01-01T10:30:00"), seats),
                new Screening(5, django, room, LocalDateTime.parse("2023-01-01T07:30:00"), LocalDateTime.parse("2023-01-01T08:30:00"), seats),
                new Screening(6, django, room, LocalDateTime.parse("2023-01-01T08:30:00"), LocalDateTime.parse("2023-01-01T09:30:00"), seats),
                new Screening(7, hateful8, room, LocalDateTime.parse("2023-01-01T14:30:00"), LocalDateTime.parse("2023-01-01T15:30:00"), seats),
                new Screening(8, hateful8, room, LocalDateTime.parse("2023-01-01T17:30:00"), LocalDateTime.parse("2023-01-01T18:30:00"), seats),
                new Screening(9, hateful8, room, LocalDateTime.parse("2023-01-01T08:30:00"), LocalDateTime.parse("2023-01-01T09:30:00"), seats)
        );
    }

    private Movie prepareMovie(long id, String title) {
        return new Movie(id, title, "lorem ipsum");
    }

    private List<Seat> prepareSeatsForRoom(Room room) {
        List<Seat> roomSeats = new ArrayList<>();
        for(int row = 1; row <= room.numberOfRows(); row++) {
            for (int seatNr = 1; seatNr <= room.numberOfSeatsInRow(); seatNr++) {
                roomSeats.add(new Seat(new SeatId(row, seatNr), SeatStatus.FREE));
            }
        }
        return roomSeats;
    }

    private Screening prepareSingleScreening() {
        Movie movie = prepareMovie(1, "Django");
        Room room = new Room("Room A", 2, 2);
        List<Seat> seats = List.of(new Seat(new SeatId(1, 1), SeatStatus.BOOKED),
                new Seat(new SeatId(1, 2), SeatStatus.BOOKED),
                new Seat(new SeatId(2, 1), SeatStatus.FREE),
                new Seat(new SeatId(2, 2), SeatStatus.FREE));
        LocalDateTime startTime = LocalDateTime.parse("2023-01-01T10:30:00");
        LocalDateTime endTime = LocalDateTime.parse("2023-01-01T12:45:00");
        return new Screening(1, movie, room, startTime, endTime, seats);
    }

}