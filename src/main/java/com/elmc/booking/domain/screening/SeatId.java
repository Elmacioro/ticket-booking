package com.elmc.booking.domain.screening;

public record SeatId(int rowNumber, int seatNumber) {

    public SeatId {
        validateParameters(rowNumber, seatNumber);
    }

    private void validateParameters(int rowNumber, int seatNumber) {
        if (rowNumber <= 0 || seatNumber <= 0) {
            throw new IllegalArgumentException("rowNumber and seatNumber cannot be lower than 1");
        }
    }
}
