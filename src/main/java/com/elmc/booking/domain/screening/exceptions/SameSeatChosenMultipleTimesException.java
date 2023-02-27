package com.elmc.booking.domain.screening.exceptions;

public class SameSeatChosenMultipleTimesException extends RuntimeException {

    public SameSeatChosenMultipleTimesException() {
        super("You must not chose the same seat for booking more than once");
    }
}
