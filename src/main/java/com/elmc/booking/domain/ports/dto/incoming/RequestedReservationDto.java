package com.elmc.booking.domain.ports.dto.incoming;

import com.elmc.booking.domain.ports.dto.shared.TicketDto;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public record RequestedReservationDto(@NonNull UUID screeningId,
                                      @NonNull String firstname,
                                      @NonNull String surname,
                                      @NonNull List<TicketDto> tickets) {
}
