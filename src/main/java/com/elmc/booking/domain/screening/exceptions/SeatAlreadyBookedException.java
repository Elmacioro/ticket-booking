package com.elmc.booking.domain.screening.exceptions;

public class SeatAlreadyBookedException extends RuntimeException {

    public SeatAlreadyBookedException() {
        super("Provided seats are not available");
    }
}
