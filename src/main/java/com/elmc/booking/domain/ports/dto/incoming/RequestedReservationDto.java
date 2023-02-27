package com.elmc.booking.domain.ports.dto.incoming;

import com.elmc.booking.domain.ports.dto.shared.TicketDto;
import lombok.NonNull;

import java.util.List;

public record RequestedReservationDto(long screeningId,
                                      @NonNull String firstName,
                                      @NonNull String surname,
                                      @NonNull List<TicketDto> tickets) {
}
