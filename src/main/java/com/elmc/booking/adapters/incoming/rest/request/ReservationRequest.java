package com.elmc.booking.adapters.incoming.rest.request;

import com.elmc.booking.domain.ports.dto.incoming.RequestedReservationDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReservationRequest(long screeningId,
                                 @NotNull String firstName,
                                 @NotNull String surname,
                                 @NotNull List<TicketData> tickets) {

        public RequestedReservationDto toDto() {
            return new RequestedReservationDto(screeningId,
                    firstName,
                    surname,
                    tickets.stream()
                            .map(TicketData::toDto)
                            .toList());
        }
}
