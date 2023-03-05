package com.elmc.booking.domain.ports.dto.shared;

import com.elmc.booking.domain.screening.Seat;
import com.elmc.booking.domain.screening.SeatId;
import lombok.NonNull;

public record SeatDto(int rowNumber, int seatNumber) {
    public SeatDto(@NonNull Seat seat) {
        this(seat.getSeatId().rowNumber(),
                seat.getSeatId().seatNumber());
    }

    public SeatId toSeatId() {
        return new SeatId(rowNumber, seatNumber);
    }

}
