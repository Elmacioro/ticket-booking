package com.elmc.booking.domain.ports.dto;

import com.elmc.booking.domain.screening.Seat;

public record SeatDto(int rowNumber, int seatInRowNumber) {
    public SeatDto(Seat seat) {
        this(seat.getRowNumber(), seat.getSeatInRowNumber());
    }
}
