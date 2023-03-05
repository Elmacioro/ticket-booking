package com.elmc.booking.domain.ports.dto.shared;

import com.elmc.booking.domain.screening.Screening;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScreeningTimeDto(
        UUID screeningId,
        @NonNull LocalDateTime start,
        @NonNull LocalDateTime end) {

    public ScreeningTimeDto(Screening screening) {
        this(screening.getId(),
                screening.getStartTime(),
                screening.getEndTime());
    }
}
