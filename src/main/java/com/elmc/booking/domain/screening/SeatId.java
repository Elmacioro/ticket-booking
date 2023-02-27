package com.elmc.booking.domain.screening;

public record SeatId(int rowNumber, int seatInRowNumber) {

    public SeatId {
        validateParameters(rowNumber, seatInRowNumber);
    }

    private void validateParameters(int rowNumber, int seatInRowNumber) {
        if (rowNumber <= 0 || seatInRowNumber <= 0) {
            throw new IllegalArgumentException("rowNumber and seatInRow cannot be lower than 1");
        }
    }
}
