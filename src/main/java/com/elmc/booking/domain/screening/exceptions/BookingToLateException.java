package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

@Getter
public class BookingToLateException extends RuntimeException {

    private final int minutesToBookBeforeScreening;

    public BookingToLateException(int minutesToBookBeforeScreening) {
        super(String.format("Booking not allowed %s minutes before screening", minutesToBookBeforeScreening));
        this.minutesToBookBeforeScreening = minutesToBookBeforeScreening;
    }
}
