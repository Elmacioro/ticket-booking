package com.elmc.booking.domain.ports.outgoing;

import com.elmc.booking.domain.reservation.Reservation;
import lombok.NonNull;

public interface ReservationRepository {

    void save(@NonNull Reservation reservation);
}
