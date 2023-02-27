package com.elmc.booking.domain.ports.outgoing;

import com.elmc.booking.domain.reservation.Reservation;

public interface ReservationRepository {

    long save(Reservation reservation);
}
