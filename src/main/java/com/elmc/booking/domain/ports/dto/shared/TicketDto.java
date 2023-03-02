package com.elmc.booking.domain.ports.dto.shared;

import com.elmc.booking.domain.reservation.Ticket;
import com.elmc.booking.domain.reservation.TicketType;
import lombok.NonNull;

public record TicketDto(@NonNull SeatDto seatDto, long ticketTypeId) {

    public Ticket toTicket(TicketType ticketType) {
        return new Ticket(seatDto.rowNumber(),
                seatDto.seatInRowNumber(),
                ticketType);
    }

}
