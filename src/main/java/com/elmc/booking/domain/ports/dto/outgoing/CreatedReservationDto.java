package com.elmc.booking.domain.ports.dto.outgoing;

import com.elmc.booking.domain.ports.dto.shared.PriceDto;
import lombok.NonNull;

import java.time.LocalDateTime;

public record CreatedReservationDto(long reservationId,
                                    @NonNull PriceDto priceDto,
                                    @NonNull LocalDateTime expirationTime) {
}
