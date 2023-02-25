package com.elmc.booking.domain.ports.dto;

import lombok.NonNull;

import java.util.List;

public record MovieScreeningDto(long movieId, @NonNull String movieTitle, @NonNull List<ScreeningTime> screeningTimes) {

}
