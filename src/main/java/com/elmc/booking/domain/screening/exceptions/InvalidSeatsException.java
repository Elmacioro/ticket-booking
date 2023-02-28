package com.elmc.booking.domain.screening.exceptions;

public class InvalidSeatsException extends RuntimeException {

    public InvalidSeatsException() {
        super("Provided seats do not exist for given screening");
    }
}
