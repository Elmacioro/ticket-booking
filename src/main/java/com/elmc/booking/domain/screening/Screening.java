package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.screening.exceptions.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
public class Screening {

    private static final int MINUTES_BEFORE_SCREENING_ALLOWED_TO_BOOK = 15;

    private final long id;

    private final Movie movie;

    private final List<Seat> seats;

    private Room room;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Screening(long id,
                     @NonNull Movie movie,
                     @NonNull Room room,
                     @NonNull LocalDateTime startTime,
                     @NonNull LocalDateTime endTime,
                     @NonNull List<Seat> seats) {
        validateParameters(room, startTime, endTime, seats);
        this.id = id;
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seats = seats;
    }


    public void bookSeats(List<SeatId> seatsToBook) {
        validateBooking(seatsToBook);
        seats.stream()
                .filter(seat -> seatsToBook.contains(seat.getSeatId()))
                .forEach(seat -> seat.setSeatStatus(SeatStatus.BOOKED));
    }

    public List<Seat> getBookedSeats() {
        return seats.stream()
                .filter(Seat::isBooked)
                .collect(Collectors.toList());
    }

    public List<Seat> getFreeSeats() {
        return seats.stream()
                .filter(Seat::isFree)
                .collect(Collectors.toList());
    }

    public boolean isSeatBooked(SeatId seatId) {
        return seats.stream()
                .filter((seat -> seat.getSeatId().rowNumber() == seatId.rowNumber() &&
                        seat.getSeatId().seatInRowNumber() == seatId.seatInRowNumber()))
                .findFirst()
                .orElseThrow(() -> new NoSuchSeatException(seatId.rowNumber(), seatId.seatInRowNumber()))
                .isBooked();

    }

    private void validateBooking(List<SeatId> seatsToBook) {
        if(!areSeatIdsValid(seatsToBook)) {
            throw new SeatsNotExistException(seatsToBook);
        }
        if (!areSeatsFree(seatsToBook)) {
            throw new SeatAlreadyBookedException(seatsToBook);
        }
        if (isTooLateForBooking()) {
            throw new BookingToLateException(MINUTES_BEFORE_SCREENING_ALLOWED_TO_BOOK);
        }
        if (isAnySeatChosenMultipleTimes(seatsToBook)) {
            throw new SameSeatChosenMultipleTimesException(seatsToBook);
        }
        if (isSingleSeatAfterBooking(seatsToBook)) {
            throw new SingleSeatLeftOutAfterBookingException(seatsToBook);
        }
    }

    private boolean isTooLateForBooking() {
        return LocalDateTime.now()
                .plusMinutes(MINUTES_BEFORE_SCREENING_ALLOWED_TO_BOOK)
                .isAfter(startTime);
    }

    private boolean isAnySeatChosenMultipleTimes(List<SeatId> seatsToBook) {
        return seatsToBook.size() != new HashSet<>(seatsToBook).size();
    }

    private boolean isSingleSeatAfterBooking(List<SeatId> seatsToBook) {
        Map<SeatId, SeatStatus> seatStatusesAfterPotentialBooking = new HashMap<>();
        seats.forEach(seat -> seatStatusesAfterPotentialBooking.put(seat.getSeatId(), seat.getSeatStatus()));
        seatsToBook.forEach(seatId -> seatStatusesAfterPotentialBooking.put(seatId, SeatStatus.BOOKED));
        for(int row = 1; row <= room.numberOfRows(); row++) {
            for(int seatNumber = 2; seatNumber <= room.numberOfSeatsInRow() - 1; seatNumber++) {
                SeatStatus previousSeat = seatStatusesAfterPotentialBooking.get(new SeatId(row, seatNumber - 1));
                SeatStatus currentSeat = seatStatusesAfterPotentialBooking.get(new SeatId(row, seatNumber));
                SeatStatus nextSeat = seatStatusesAfterPotentialBooking.get(new SeatId(row, seatNumber + 1));
                if (previousSeat == SeatStatus.BOOKED && currentSeat == SeatStatus.FREE && nextSeat == SeatStatus.BOOKED) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean areSeatIdsValid(List<SeatId> seatsToBook) {
        return seats.stream()
                .map(Seat::getSeatId)
                .collect(Collectors.toSet())
                .containsAll(seatsToBook);
    }

    private boolean areSeatsFree(List<SeatId> seatsToBook) {
        return getFreeSeats().stream()
                .map(Seat::getSeatId)
                .collect(Collectors.toSet())
                .containsAll(seatsToBook);
    }

    private void validateParameters(Room room,
                                    LocalDateTime startTime,
                                    LocalDateTime endTime,
                                    List<Seat> seats) {
        if (startTime.isAfter(endTime)) {
            throw new InvalidScreeningTimeIntervalException(startTime, endTime);
        }
        int seatsInRoom = room.numberOfRows() * room.numberOfSeatsInRow();
        if (seatsInRoom != seats.size()) {
            throw new IllegalArgumentException("Number of seats has to match number of seats in the room");
        }
    }
}
