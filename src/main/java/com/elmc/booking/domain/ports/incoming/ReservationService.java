package com.elmc.booking.domain.ports.incoming;

import com.elmc.booking.domain.ports.dto.incoming.RequestedReservationDto;
import com.elmc.booking.domain.ports.dto.outgoing.CreatedReservationDto;

public interface ReservationService {

    CreatedReservationDto bookSeats(RequestedReservationDto requestedReservationDto);

}
