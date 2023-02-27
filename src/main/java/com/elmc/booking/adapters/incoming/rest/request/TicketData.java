package com.elmc.booking.adapters.incoming.rest.request;

import com.elmc.booking.domain.ports.dto.shared.SeatDto;
import com.elmc.booking.domain.ports.dto.shared.TicketDto;
import jakarta.validation.constraints.NotNull;

public record TicketData(int row,
                         int seatInRow,
                         @NotNull String ticketType) {

    public TicketDto toDto() {
        return new TicketDto(new SeatDto(row, seatInRow),
                ticketType);
    }
}
