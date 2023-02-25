package com.elmc.booking.domain.screening;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = {"rowNumber", "seatInRowNumber"})
public final class Seat {

    private final int rowNumber;
    private final int seatInRowNumber;

    @Setter
    @NonNull
    private SeatStatus seatStatus;

    public Seat(int rowNumber, int seatInRowNumber, @NonNull SeatStatus seatStatus) {
        validateParameters(rowNumber, seatInRowNumber);
        this.rowNumber = rowNumber;
        this.seatInRowNumber = seatInRowNumber;
        this.seatStatus = seatStatus;
    }

    private static void validateParameters(int rowNumber, int seatInRowNumber) {
        if (rowNumber <= 0 || seatInRowNumber <= 0) {
            throw new IllegalArgumentException("rowNumber and seatInRow cannot be lower than 1");
        }
    }

    public boolean isSeatBooked() {
        return seatStatus == SeatStatus.BOOKED;
    }

    public boolean isSeatFree() {
        return seatStatus == SeatStatus.FREE;
    }

}
