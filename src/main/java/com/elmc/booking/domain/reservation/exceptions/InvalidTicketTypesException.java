package com.elmc.booking.domain.reservation.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidTicketTypesException extends RuntimeException {

    private final List<Long> providedTicketTypesIds;

    public InvalidTicketTypesException(List<Long> providedTicketTypesIds) {
        super("Provided ticket types do not exist in the system");
        this.providedTicketTypesIds = providedTicketTypesIds;
    }
}
