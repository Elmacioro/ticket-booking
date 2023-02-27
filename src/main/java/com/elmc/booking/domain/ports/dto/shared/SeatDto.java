package com.elmc.booking.domain.ports.dto.shared;

import com.elmc.booking.domain.screening.Seat;
import com.elmc.booking.domain.screening.SeatId;

public record SeatDto(int rowNumber, int seatInRowNumber) {
    public SeatDto(Seat seat) {
        this(seat.getSeatId().rowNumber(),
                seat.getSeatId().seatInRowNumber());
    }

    public SeatId toSeatId() {
        return new SeatId(rowNumber, seatInRowNumber);
    }

}
