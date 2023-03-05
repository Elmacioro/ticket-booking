package com.elmc.booking.adapters.outgoing.database.validation;

public class LocalDateTimeValidationException extends RuntimeException {

    public LocalDateTimeValidationException(Throwable cause) {
        super("Failed to validate LocalDateTime range", cause);
    }
}
