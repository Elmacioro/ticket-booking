package com.elmc.booking.domain.ports.dto.outgoing;

import com.elmc.booking.domain.ports.dto.shared.ScreeningTimeDto;
import lombok.NonNull;

import java.util.List;

public record MovieScreeningsDto(long movieId, @NonNull String movieTitle, @NonNull List<ScreeningTimeDto> screeningTimes) {

}
