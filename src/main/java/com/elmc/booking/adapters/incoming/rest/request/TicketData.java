package com.elmc.booking.adapters.incoming.rest.request;

import com.elmc.booking.domain.ports.dto.shared.SeatDto;
import com.elmc.booking.domain.ports.dto.shared.TicketDto;

public record TicketData(int row,
                         int seatInRow,
                         long ticketTypeId) {

    public TicketDto toDto() {
        return new TicketDto(new SeatDto(row, seatInRow),
                ticketTypeId);
    }
}
