package com.elmc.booking.domain.reservation;

import lombok.NonNull;

public record Ticket(int rowNumber, int seatInRowNumber, @NonNull TicketType ticketType) {

    public Ticket {
        validateParameters(rowNumber, seatInRowNumber);
    }

    private void validateParameters(int rowNumber, int seatInRowNumber) {
        if (rowNumber <= 0 || seatInRowNumber <= 0) {
            throw new IllegalArgumentException("rowNumber and seatInRow cannot be lower than 1");
        }
    }
}
