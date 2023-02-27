package com.elmc.booking.domain.reservation;

import lombok.NonNull;

import java.math.BigDecimal;

public record Price(@NonNull BigDecimal amount, @NonNull String currency) {

    public Price {
        validateParameters(amount, currency);
    }

    private void validateParameters(BigDecimal amount, String currency) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price amount cannot be lower than 0");
        }
        if (currency.length() != 3) {
            throw new IllegalArgumentException("Currency must be represented as 3 letter code");
        }
    }
}
