package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import com.elmc.booking.domain.screening.exceptions.NoSuchSeatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningTest {

    private final long screeningId = 1;
    private Movie movie;
    private Room room;
    private Set<Seat> bookedSeats;
    private Set<Seat> freeSeats;
    private Set<Seat> allSeats;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        movie = new Movie(1, "Django", "Lorem ipsum");
        room = new Room("Room A", 2, 2);
        bookedSeats = Set.of(new Seat(1, 1, SeatStatus.BOOKED), new Seat(1, 2, SeatStatus.BOOKED));
        freeSeats = Set.of(new Seat(2, 1, SeatStatus.FREE), new Seat(2, 2, SeatStatus.FREE));
        allSeats = Stream.concat(bookedSeats.stream(), freeSeats.stream()).collect(Collectors.toSet());
        startTime = LocalDateTime.parse("2023-01-01T10:30:00");
        endTime = LocalDateTime.parse("2023-01-01T12:45:00");
    }

    @Test
    public void constructorShouldThrowExceptionWhenEndTimeBeforeStartTime() {
        startTime = LocalDateTime.parse("2023-01-01T10:30:00");
        endTime = LocalDateTime.parse("2023-01-01T09:20:00");

        assertThrows(InvalidScreeningTimeIntervalException.class,
                () -> new Screening(screeningId, movie, room, startTime, endTime, allSeats));
    }

    @Test
    public void constructorShouldThrowExceptionWhenRoomNotMatchNumberOfSeats() {
        room = new Room("Room B", 10, 10);

        assertThrows(IllegalArgumentException.class,
                () -> new Screening(screeningId, movie, room, startTime, endTime, allSeats));
    }

    @Test
    public void isSeatBookedShouldThrowExceptionWhenSeatNotExist() {
        Screening screening = new Screening(screeningId, movie, room, startTime, endTime, allSeats);

        assertThrows(NoSuchSeatException.class,
                () -> screening.isSeatBooked(100, 100));
    }

    @Test
    public void isSeatBookedShouldReturnFalseWhenSeatIsFree() {
        Screening screening = new Screening(screeningId, movie, room, startTime, endTime, allSeats);
        assertFalse(screening.isSeatBooked(2, 2));
    }

    @Test
    public void isSeatBookedShouldReturnTrueWhenSeatIsBooked() {
        Screening screening = new Screening(screeningId, movie, room, startTime, endTime, allSeats);
        assertTrue(screening.isSeatBooked(1, 1));
    }

    @Test
    public void getBookedSeatsShouldReturnOnlyBookedSeats() {
        Screening screening = new Screening(screeningId, movie, room, startTime, endTime, allSeats);
        assertIterableEquals(bookedSeats, screening.getBookedSeats());
    }

    @Test
    public void getFreeSeatsShouldReturnOnlyFreeSeats() {
        Screening screening = new Screening(screeningId, movie, room, startTime, endTime, allSeats);
        assertIterableEquals(freeSeats, screening.getFreeSeats());
    }

}