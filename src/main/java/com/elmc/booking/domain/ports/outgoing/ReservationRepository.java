package com.elmc.booking.domain.ports.outgoing;

import com.elmc.booking.domain.reservation.Reservation;

public interface ReservationRepository {

    void save(Reservation reservation);
}
