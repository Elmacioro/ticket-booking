package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

@Getter
public class BookingToLateException extends RuntimeException {

    private final int minutesToBookBeforeScreening;

    public BookingToLateException(int minutesToBookBeforeScreening) {
        super("Booking not allowed %s minutes before screening".formatted(minutesToBookBeforeScreening));
        this.minutesToBookBeforeScreening = minutesToBookBeforeScreening;
    }
}
