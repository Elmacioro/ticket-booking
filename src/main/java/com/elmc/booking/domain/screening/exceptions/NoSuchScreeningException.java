package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NoSuchScreeningException extends RuntimeException {

    private final UUID screeningId;

    public NoSuchScreeningException(UUID screeningId) {
        super("No screening found for screeningId of: %s".formatted(screeningId));
        this.screeningId = screeningId;
    }
}
