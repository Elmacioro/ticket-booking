package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

@Getter
public class NoSuchSeatException extends RuntimeException {

    private final int rowNumber;
    private final int seatInRowNumber;

    public NoSuchSeatException(int rowNumber, int seatInRowNumber) {
        super(String.format("There is no such seat with [rowNumber: %d, seatInRowNumber%d] for the screening",
                rowNumber,
                seatInRowNumber));
        this.rowNumber = rowNumber;
        this.seatInRowNumber = seatInRowNumber;
    }
}
