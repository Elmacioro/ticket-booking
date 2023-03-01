package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

@Getter
public class NoSuchSeatException extends RuntimeException {

    private final int rowNumber;
    private final int seatInRowNumber;

    public NoSuchSeatException(int rowNumber, int seatInRowNumber) {
        super("There is no such seat with [rowNumber: %d, seatInRowNumber%d] for the screening"
                .formatted(rowNumber, seatInRowNumber));
        this.rowNumber = rowNumber;
        this.seatInRowNumber = seatInRowNumber;
    }
}
