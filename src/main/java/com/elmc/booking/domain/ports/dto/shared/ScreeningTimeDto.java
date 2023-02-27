package com.elmc.booking.domain.ports.dto.shared;

import com.elmc.booking.domain.screening.Screening;
import lombok.NonNull;

import java.time.LocalDateTime;

public record ScreeningTimeDto(
        long screeningId,
        @NonNull LocalDateTime start,
        @NonNull LocalDateTime end) {

    public ScreeningTimeDto(Screening screening) {
        this(screening.getId(),
                screening.getStartTime(),
                screening.getEndTime());
    }
}
