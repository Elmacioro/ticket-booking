package com.elmc.booking.domain.reservation;

import lombok.NonNull;

public record TicketType(long id, @NonNull String name, @NonNull Price price) {

    public TicketType {
        validateParameters(name);
    }

    private void validateParameters(String name) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("Ticket type cannot be blank");
        }
    }
}
