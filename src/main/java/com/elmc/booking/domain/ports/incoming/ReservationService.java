package com.elmc.booking.domain.ports.incoming;

import com.elmc.booking.domain.ports.dto.incoming.RequestedReservationDto;
import com.elmc.booking.domain.ports.dto.outgoing.CreatedReservationDto;
import lombok.NonNull;

public interface ReservationService {

    CreatedReservationDto bookSeats(@NonNull RequestedReservationDto requestedReservationDto);

}
