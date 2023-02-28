package com.elmc.booking.domain.reservation.exceptions;

public class InvalidTicketTypesException extends RuntimeException {

    public InvalidTicketTypesException() {
        super("Provided ticket types do not exist in the system");
    }
}
