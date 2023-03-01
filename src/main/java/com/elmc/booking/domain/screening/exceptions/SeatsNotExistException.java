package com.elmc.booking.domain.screening.exceptions;

import com.elmc.booking.domain.screening.SeatId;

import java.util.List;

public class SeatsNotExistException extends BookingInvalidSeatsException {

    public SeatsNotExistException(List<SeatId> seatsToBook) {
        super("Provided seats do not exist for given screening", seatsToBook);
    }

}
