package com.elmc.booking.domain.ports.dto.shared;

import com.elmc.booking.domain.reservation.Ticket;
import com.elmc.booking.domain.reservation.TicketType;
import lombok.NonNull;

public record TicketDto(@NonNull SeatDto seatDto, @NonNull String ticketTypeName) {

    public Ticket toTicket(@NonNull TicketType ticketType) {
        return new Ticket(seatDto.rowNumber(),
                seatDto.seatNumber(),
                ticketType);
    }

}
