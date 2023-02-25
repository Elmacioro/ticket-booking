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

    @NonNull
    private final Movie movie;

    @NonNull
    private final Set<Seat> seats;

    @NonNull
    private final Room room;

    @NonNull
    private final LocalDateTime startTime;

    @NonNull
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
        return !isSeatTaken(rowNumber, seatInRowNumber);
    }

    public boolean isSeatTaken(int rowNumber, int seatInRowNumber) {
        return seats.stream()
                .filter((seat -> seat.getRowNumber() == rowNumber && seat.getSeatInRowNumber() == seatInRowNumber))
                .findFirst()
                .orElseThrow(() -> new NoSuchSeatException(rowNumber, seatInRowNumber))
                .isSeatBooked();

    }

    private void validateParameters(Room room,
                                    LocalDateTime startTime,
                                    LocalDateTime endTime,
                                    @NonNull Set<Seat> seats) {
        if (startTime.isAfter(endTime)) {
            throw new InvalidScreeningTimeIntervalException();
        }
        int seatsInRoom = room.numberOfRows() * room.numberOfSeatsInRow();
        if (seatsInRoom != seats.size()) {
            throw new IllegalArgumentException("Number of seats has to match number of seats in the room");
        }
    }
}
