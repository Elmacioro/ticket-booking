package com.elmc.booking.domain.ports.dto;

import com.elmc.booking.domain.screening.Screening;
import lombok.NonNull;

import java.time.LocalDateTime;

public record ScreeningTime(
        long screeningId,
        @NonNull LocalDateTime start,
        @NonNull LocalDateTime end) {

    public ScreeningTime(Screening screening) {
        this(screening.getId(),
                screening.getStartTime(),
                screening.getEndTime());
    }
}
