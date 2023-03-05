package com.elmc.booking.adapters.incoming.rest.request;

import com.elmc.booking.domain.ports.dto.incoming.RequestedReservationDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ReservationRequest(@NotNull UUID screeningId,
                                 @NotNull String firstname,
                                 @NotNull String surname,
                                 @NotNull List<TicketData> tickets) {

        public RequestedReservationDto toDto() {
            return new RequestedReservationDto(screeningId,
                    firstname,
                    surname,
                    tickets.stream()
                            .map(TicketData::toDto)
                            .toList());
        }
}
