package com.elmc.booking.adapters.incoming.rest.request;

import com.elmc.booking.domain.ports.dto.shared.SeatDto;
import com.elmc.booking.domain.ports.dto.shared.TicketDto;
import jakarta.validation.constraints.NotNull;

public record TicketData(int row,
                         int seatNumber,
                         @NotNull String ticketTypeName) {

    public TicketDto toDto() {
        return new TicketDto(new SeatDto(row, seatNumber),
                ticketTypeName);
    }
}
