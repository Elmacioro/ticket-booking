package com.elmc.booking.domain.reservation.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidTicketTypesException extends RuntimeException {

    private final List<String> providedTicketTypeNames;

    public InvalidTicketTypesException(List<String> providedTicketTypeNames) {
        super("Provided ticket types do not exist in the system");
        this.providedTicketTypeNames = providedTicketTypeNames;
    }
}
