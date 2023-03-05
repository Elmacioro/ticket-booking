package com.elmc.booking.domain.screening.exceptions;

import com.elmc.booking.domain.screening.SeatId;

import java.util.List;

public class SeatAlreadyBookedException extends BookingInvalidSeatsException {

    public SeatAlreadyBookedException(List<SeatId> seatsToBook) {
        super("Provided seats are already booked", seatsToBook);
    }
}
