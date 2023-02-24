package com.elmc.booking.domain.screening.exceptions;

public class NoSuchScreeningException extends RuntimeException {

    public NoSuchScreeningException(long screeningId) {
        super(String.format("No screening found for screeningId of: %d", screeningId));
    }
}
