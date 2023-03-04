package com.elmc.booking.domain.reservation;

import lombok.NonNull;

public record Ticket(int rowNumber, int seatNumber, @NonNull TicketType ticketType) {

    public Ticket {
        validateParameters(rowNumber, seatNumber);
    }

    private void validateParameters(int rowNumber, int seatNumber) {
        if (rowNumber <= 0 || seatNumber <= 0) {
            throw new IllegalArgumentException("rowNumber and seatNumber cannot be lower than 1");
        }
    }
}
