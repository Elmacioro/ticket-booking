package com.elmc.booking.domain.ports.dto.shared;

import com.elmc.booking.domain.reservation.Price;
import lombok.NonNull;

import java.math.BigDecimal;

public record PriceDto(@NonNull String currency,
                       @NonNull BigDecimal amount) {
    public PriceDto(@NonNull Price price) {
        this(price.currency(), price.amount());
    }
}
