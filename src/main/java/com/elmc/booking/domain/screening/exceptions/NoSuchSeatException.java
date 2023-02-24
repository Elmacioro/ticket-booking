package com.elmc.booking.domain.screening.exceptions;

public class NoSuchSeatException extends RuntimeException {

    public NoSuchSeatException(int rowNumber, int seatInRowNumber) {
        super(String.format("There is no such seat with [rowNumber: %d, seatInRowNumber%d] for the screening",
                rowNumber,
                seatInRowNumber));
    }
}
