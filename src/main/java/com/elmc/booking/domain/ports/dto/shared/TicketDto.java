package com.elmc.booking.domain.ports.dto.shared;

import lombok.NonNull;

public record TicketDto(@NonNull SeatDto seatDto, long ticketTypeId) {

}
