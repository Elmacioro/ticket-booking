package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.screening.exceptions.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@ToString
public class Screening {

    private static final int MINUTES_BEFORE_SCREENING_ALLOWED_TO_BOOK = 15;

    private final UUID id;

    private final Movie movie;

    private final List<Seat> seats;

    private final Room room;

    private final LocalDateTime startTime;

    private final LocalDateTime endTime;

    public Screening(@NonNull UUID id,
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


    public void bookSeats(@NonNull List<SeatId> seatsToBook) {
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

    public List<Seat> getAvailableSeats() {
        return seats.stream()
                .filter(Seat::isAvailable)
                .collect(Collectors.toList());
    }

    public boolean isSeatBooked(@NonNull SeatId seatId) {
        return seats.stream()
                .filter((seat -> seat.getSeatId().rowNumber() == seatId.rowNumber() &&
                        seat.getSeatId().seatNumber() == seatId.seatNumber()))
                .findFirst()
                .orElseThrow(() -> new NoSuchSeatException(seatId.rowNumber(), seatId.seatNumber()))
                .isBooked();
    }

    private void validateBooking(List<SeatId> seatsToBook) {
        validateSeatsExist(seatsToBook);
        validateSeatsAreAvailable(seatsToBook);
        validateIsNotTooLateForBooking();
        validateNoSeatIsChosenMultipleTimes(seatsToBook);
        validateNoSingleSeatLeftOutAfterBooking(seatsToBook);
    }

    private void validateSeatsExist(List<SeatId> seatsToBook) {
        boolean seatsExist = seats.stream()
                .map(Seat::getSeatId)
                .collect(Collectors.toSet())
                .containsAll(seatsToBook);
        if (!seatsExist) {
            throw new SeatsNotExistException(seatsToBook);
        }
    }

    private void validateSeatsAreAvailable(List<SeatId> seatsToBook) {
        boolean areSeatsAvailable = getAvailableSeats().stream()
                .map(Seat::getSeatId)
                .collect(Collectors.toSet())
                .containsAll(seatsToBook);
        if (!areSeatsAvailable) {
            throw new SeatAlreadyBookedException(seatsToBook);
        }
    }

    private void validateIsNotTooLateForBooking() {
        boolean isTooLateForBooking = LocalDateTime.now()
                .plusMinutes(MINUTES_BEFORE_SCREENING_ALLOWED_TO_BOOK)
                .isAfter(startTime);
        if (isTooLateForBooking) {
            throw new BookingToLateException(MINUTES_BEFORE_SCREENING_ALLOWED_TO_BOOK);
        }
    }

    private void validateNoSeatIsChosenMultipleTimes(List<SeatId> seatsToBook) {
        if (seatsToBook.size() != new HashSet<>(seatsToBook).size()) {
            throw new SameSeatChosenMultipleTimesException(seatsToBook);
        }
    }

    private void validateNoSingleSeatLeftOutAfterBooking(List<SeatId> seatsToBook) {
        List<Integer> affectedRows = seatsToBook.stream().map(SeatId::rowNumber).toList();
        Map<SeatId, SeatStatus> seatStatusesAfterPotentialBooking = getSeatStatusesAfterPotentialBooking(seatsToBook);
        affectedRows.forEach(rowNumber -> {
            for(int seatNumber = 2; seatNumber <= room.seatsInRowNumber() - 1; seatNumber++) {
                SeatStatus previousSeat = seatStatusesAfterPotentialBooking.get(new SeatId(rowNumber, seatNumber - 1));
                SeatStatus currentSeat = seatStatusesAfterPotentialBooking.get(new SeatId(rowNumber, seatNumber));
                SeatStatus nextSeat = seatStatusesAfterPotentialBooking.get(new SeatId(rowNumber, seatNumber + 1));
                if (previousSeat == SeatStatus.BOOKED && currentSeat == SeatStatus.AVAILABLE && nextSeat == SeatStatus.BOOKED) {
                    throw new SingleSeatLeftOutAfterBookingException(seatsToBook);
                }
            }
        });
    }

    private Map<SeatId, SeatStatus> getSeatStatusesAfterPotentialBooking(List<SeatId> seatsToBook) {
        Map<SeatId, SeatStatus> seatStatusesAfterPotentialBooking = seats.stream()
                .collect(Collectors.toMap(Seat::getSeatId, Seat::getSeatStatus));
        seatsToBook.forEach(seatId -> seatStatusesAfterPotentialBooking.put(seatId, SeatStatus.BOOKED));
        return seatStatusesAfterPotentialBooking;
    }

    private void validateParameters(Room room,
                                    LocalDateTime startTime,
                                    LocalDateTime endTime,
                                    List<Seat> seats) {
        if (startTime.isAfter(endTime)) {
            throw new InvalidScreeningTimeIntervalException(startTime, endTime);
        }
        int seatsInRoom = room.rowsNumber() * room.seatsInRowNumber();
        if (seatsInRoom != seats.size()) {
            throw new IllegalArgumentException("Number of seats has to match number of seats in the room");
        }
    }
}
