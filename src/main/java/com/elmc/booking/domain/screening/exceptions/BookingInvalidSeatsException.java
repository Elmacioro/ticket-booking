package com.elmc.booking.domain.screening.exceptions;

import com.elmc.booking.domain.screening.SeatId;
import lombok.Getter;

import java.util.List;

@Getter
public class BookingInvalidSeatsException extends RuntimeException {

    protected final List<SeatId> seatsToBook;

    public BookingInvalidSeatsException(String message, List<SeatId> seatsToBook) {
        super(message);
        this.seatsToBook = seatsToBook;
    }
}
