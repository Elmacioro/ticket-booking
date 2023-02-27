package com.elmc.booking.domain.reservation;

import lombok.NonNull;

public record TicketType(long id, @NonNull String type, @NonNull Price price) {

    public TicketType {
        validateParameters(type);
    }

    private void validateParameters(String type) {
        if(type.isBlank()) {
            throw new IllegalArgumentException("Ticket type cannot be blank");
        }
    }
}
