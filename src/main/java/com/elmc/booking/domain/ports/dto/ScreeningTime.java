package com.elmc.booking.domain.ports.dto;

import lombok.NonNull;

import java.time.LocalDateTime;

public record ScreeningTime(
        long screeningId,
        @NonNull LocalDateTime start,
        @NonNull LocalDateTime end) {
}
