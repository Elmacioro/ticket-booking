package com.elmc.booking.domain.screening;

import lombok.NonNull;
import lombok.Setter;

public record Seat(int rowNumber,
                   int seatInRowNumber,
                   @NonNull @Setter SeatStatus seatStatus) {
    public Seat {
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
