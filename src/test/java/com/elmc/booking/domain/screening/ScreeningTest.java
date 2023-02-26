package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import com.elmc.booking.domain.screening.exceptions.NoSuchSeatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ScreeningTest {

    private final long screeningId = 1;
    private Movie movie;
    private Room room;
    private List<Seat> bookedSeats;
    private List<Seat> freeSeats;
    private List<Seat> allSeats;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Screening screening;

    @BeforeEach
    void setUp() {
        movie = new Movie(1, "Django", "Lorem ipsum");
        room = new Room("Room A", 2, 2);
        bookedSeats = List.of(new Seat(1, 1, SeatStatus.BOOKED), new Seat(1, 2, SeatStatus.BOOKED));
        freeSeats = List.of(new Seat(2, 1, SeatStatus.FREE), new Seat(2, 2, SeatStatus.FREE));
        allSeats = Stream.concat(bookedSeats.stream(), freeSeats.stream()).toList();
        startTime = LocalDateTime.parse("2023-01-01T10:30:00");
        endTime = LocalDateTime.parse("2023-01-01T12:45:00");
        screening = new Screening(screeningId, movie, room, startTime, endTime, allSeats);
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
        assertThrows(NoSuchSeatException.class,
                () -> screening.isSeatBooked(100, 100));
    }

    @Test
    public void isSeatBookedShouldReturnFalseWhenSeatIsFree() {
        assertFalse(screening.isSeatBooked(2, 2));
    }

    @Test
    public void isSeatBookedShouldReturnTrueWhenSeatIsBooked() {
        assertTrue(screening.isSeatBooked(1, 1));
    }

    @Test
    public void getBookedSeatsShouldReturnOnlyBookedSeats() {
        assertThat(bookedSeats).hasSameElementsAs(screening.getBookedSeats());
    }

    @Test
    public void getFreeSeatsShouldReturnOnlyFreeSeats() {
        assertThat(freeSeats).hasSameElementsAs(screening.getFreeSeats());
    }

}