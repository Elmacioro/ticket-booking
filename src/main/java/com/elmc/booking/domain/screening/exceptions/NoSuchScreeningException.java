package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

@Getter
public class NoSuchScreeningException extends RuntimeException {

    private final long screeningId;

    public NoSuchScreeningException(long screeningId) {
        super(String.format("No screening found for screeningId of: %d", screeningId));
        this.screeningId = screeningId;
    }
}
