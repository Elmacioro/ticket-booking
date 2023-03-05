package com.elmc.booking.domain.ports.dto.outgoing;

import com.elmc.booking.domain.ports.dto.shared.PriceDto;
import com.elmc.booking.domain.reservation.Reservation;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatedReservationDto(UUID reservationId,
                                    @NonNull PriceDto priceDto,
                                    @NonNull LocalDateTime expirationTime) {

    public CreatedReservationDto(Reservation reservation) {
        this(reservation.getReservationId(),
                new PriceDto(reservation.calculateTotalPrice()),
                reservation.getExpirationDate());
    }
}
