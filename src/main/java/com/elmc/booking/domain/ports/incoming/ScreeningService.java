package com.elmc.booking.domain.ports.incoming;

import com.elmc.booking.domain.ports.dto.MovieScreeningDto;
import com.elmc.booking.domain.ports.dto.ScreeningDetailsDto;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {
    List<MovieScreeningDto> searchForMovieScreenings(@NonNull LocalDateTime start, @NonNull LocalDateTime end);

    ScreeningDetailsDto getScreeningDetails(long screeningId);
}
