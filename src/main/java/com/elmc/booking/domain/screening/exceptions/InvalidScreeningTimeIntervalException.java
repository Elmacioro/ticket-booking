package com.elmc.booking.domain.screening.exceptions;

public class InvalidScreeningTimeIntervalException extends RuntimeException {

    public InvalidScreeningTimeIntervalException() {
        super("Screening startTime has to be before endTime");
    }
}
