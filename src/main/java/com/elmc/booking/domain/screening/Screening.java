package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import com.elmc.booking.domain.screening.exceptions.NoSuchSeatException;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Screening {

    private final long id;

    private final Movie movie;

    private final Set<Seat> seats;

    private final Room room;

    private final LocalDateTime startTime;

    private final LocalDateTime endTime;

    public Screening(long id,
                     @NonNull Movie movie,
                     @NonNull Room room,
                     @NonNull LocalDateTime startTime,
                     @NonNull LocalDateTime endTime,
                     @NonNull Set<Seat> seats) {
        validateParameters(room, startTime, endTime, seats);
        this.id = id;
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seats = seats;
    }

    public List<Seat> getBookedSeats() {
        return seats.stream()
                .filter(Seat::isSeatBooked)
                .collect(Collectors.toList());
    }

    public List<Seat> getFreeSeats() {
        return seats.stream()
                .filter(Seat::isSeatFree)
                .collect(Collectors.toList());
    }

    public boolean isSeatFree(int rowNumber, int seatInRowNumber) {
        return !isSeatBooked(rowNumber, seatInRowNumber);
    }

    public boolean isSeatBooked(int rowNumber, int seatInRowNumber) {
        return seats.stream()
                .filter((seat -> seat.getRowNumber() == rowNumber && seat.getSeatInRowNumber() == seatInRowNumber))
                .findFirst()
                .orElseThrow(() -> new NoSuchSeatException(rowNumber, seatInRowNumber))
                .isSeatBooked();

    }

    private void validateParameters(Room room,
                                    LocalDateTime startTime,
                                    LocalDateTime endTime,
                                    Set<Seat> seats) {
        if (startTime.isAfter(endTime)) {
            throw new InvalidScreeningTimeIntervalException(startTime, endTime);
        }
        int seatsInRoom = room.numberOfRows() * room.numberOfSeatsInRow();
        if (seatsInRoom != seats.size()) {
            throw new IllegalArgumentException("Number of seats has to match number of seats in the room");
        }
    }
}
