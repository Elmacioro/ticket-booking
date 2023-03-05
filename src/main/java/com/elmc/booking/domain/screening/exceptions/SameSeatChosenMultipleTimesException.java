package com.elmc.booking.domain.screening.exceptions;

import com.elmc.booking.domain.screening.SeatId;

import java.util.List;

public class SameSeatChosenMultipleTimesException extends BookingInvalidSeatsException {

    public SameSeatChosenMultipleTimesException(List<SeatId> seatsToBook) {
        super("You must not chose the same seat for booking more than once", seatsToBook);
    }
}
