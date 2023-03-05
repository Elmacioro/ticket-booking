package com.elmc.booking.domain.reservation.exceptions;

public class NoTicketsForReservationException extends RuntimeException {

    public NoTicketsForReservationException() {
        super("No tickets provided for reservation");
    }
}
