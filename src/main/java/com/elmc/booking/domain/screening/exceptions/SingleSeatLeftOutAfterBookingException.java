package com.elmc.booking.domain.screening.exceptions;

import com.elmc.booking.domain.screening.SeatId;

import java.util.List;

public class SingleSeatLeftOutAfterBookingException extends BookingInvalidSeatsException {

    public SingleSeatLeftOutAfterBookingException(List<SeatId> seatsToBook) {
        super("Single seat would be left out after booking seats", seatsToBook);
    }
}
