package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.screening.exceptions.*;
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
        room = new Room("Room A", 2, 3);
        bookedSeats = List.of(new Seat(new SeatId(1, 1), SeatStatus.BOOKED), new Seat(new SeatId(1, 2), SeatStatus.BOOKED));
        freeSeats = List.of(
                new Seat(new SeatId(1, 3), SeatStatus.FREE),
                new Seat(new SeatId(2, 1), SeatStatus.FREE),
                new Seat(new SeatId(2, 2), SeatStatus.FREE),
                new Seat(new SeatId(2, 3), SeatStatus.FREE));
        allSeats = Stream.concat(bookedSeats.stream(), freeSeats.stream()).toList();
        startTime = LocalDateTime.now().plusDays(5);
        endTime = startTime.plusHours(2);
        screening = new Screening(screeningId, movie, room, startTime, endTime, allSeats);
    }

    @Test
    public void bookSeatsShouldCorrectlyBoolSeats() {
        List<SeatId> validSeats = List.of(new SeatId(2, 1), new SeatId(2, 2));

        screening.bookSeats(validSeats);

        int foundMatches = screening.getBookedSeats()
                .stream()
                .filter(seat -> validSeats.contains(seat.getSeatId()))
                .toList()
                .size();
        assertEquals(validSeats.size(), foundMatches);
    }

    @Test
    public void bookSeatsShouldThrowExceptionWhenSingleSeatLeftOut() {
        List<SeatId> invalidSeats = List.of(new SeatId(2, 1), new SeatId(2, 3));

        assertThrows(SingleSeatLeftOutAfterBookingException.class,
                () -> screening.bookSeats(invalidSeats));
    }

    @Test
    public void bookSeatsShouldThrowExceptionWhenBookingSameSeatTwice() {
        List<SeatId> invalidSeats = List.of(new SeatId(2, 2), new SeatId(2, 2));

        assertThrows(SameSeatChosenMultipleTimesException.class,
                () -> screening.bookSeats(invalidSeats));
    }

    @Test
    public void bookSeatsShouldThrowExceptionWhen15MinutesToScreening() {
        List<SeatId> invalidSeats = List.of(new SeatId(2, 1), new SeatId(2, 2));
        screening = new Screening(screeningId,
                movie,
                room,
                LocalDateTime.now().plusMinutes(10),
                LocalDateTime.now().plusHours(2),
                allSeats);

        assertThrows(BookingToLateException.class,
                () -> screening.bookSeats(invalidSeats));
    }

    @Test
    public void bookSeatsShouldThrowExceptionWhenSeatsAreNotFree() {
        List<SeatId> invalidSeats = List.of(new SeatId(1, 1), new SeatId(1, 2));

        assertThrows(SeatAlreadyBookedException.class,
                () -> screening.bookSeats(invalidSeats));
    }

    @Test
    public void bookSeatsShouldThrowExceptionWhenSeatsNotExist() {
        List<SeatId> invalidSeats = List.of(new SeatId(15, 15), new SeatId(1, 3));

        assertThrows(SeatsNotExistException.class,
                () -> screening.bookSeats(invalidSeats));
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
                () -> screening.isSeatBooked(new SeatId(100, 100)));
    }

    @Test
    public void isSeatBookedShouldReturnFalseWhenSeatIsFree() {
        assertFalse(screening.isSeatBooked(new SeatId(2, 2)));
    }

    @Test
    public void isSeatBookedShouldReturnTrueWhenSeatIsBooked() {
        assertTrue(screening.isSeatBooked(new SeatId(1, 1)));
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