package com.elmc.booking.domain.screening.exceptions;

public class SingleSeatLeftOutAfterBookingException extends RuntimeException {

    public SingleSeatLeftOutAfterBookingException() {
        super("Single seat would be left out after booking seats");
    }
}
