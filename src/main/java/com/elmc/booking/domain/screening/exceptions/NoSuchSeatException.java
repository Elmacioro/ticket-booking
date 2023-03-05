package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

@Getter
public class NoSuchSeatException extends RuntimeException {

    private final int rowNumber;
    private final int seatNumber;

    public NoSuchSeatException(int rowNumber, int seatNumber) {
        super("There is no such seat with [rowNumber: %d, seatInRowNumber%d] for the screening"
                .formatted(rowNumber, seatNumber));
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }
}
