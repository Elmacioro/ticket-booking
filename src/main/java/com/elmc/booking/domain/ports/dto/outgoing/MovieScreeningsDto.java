package com.elmc.booking.domain.ports.dto.outgoing;

import com.elmc.booking.domain.ports.dto.shared.ScreeningTimeDto;
import com.elmc.booking.domain.screening.Movie;
import com.elmc.booking.domain.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
@Getter
public final class MovieScreeningsDto {
    private final long movieId;
    private final @NonNull String movieTitle;
    private final @NonNull List<ScreeningTimeDto> screeningTimes;

    public MovieScreeningsDto(Movie movie, List<Screening> screeningTimes) {
        this.movieId = movie.id();
        this.movieTitle = movie.title();
        this.screeningTimes = screeningTimes.stream()
                .map(ScreeningTimeDto::new)
                .toList();
    }
}
