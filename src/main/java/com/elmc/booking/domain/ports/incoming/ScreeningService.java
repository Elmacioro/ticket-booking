package com.elmc.booking.domain.ports.incoming;

import com.elmc.booking.domain.ports.dto.outgoing.MovieScreeningsDto;
import com.elmc.booking.domain.ports.dto.outgoing.ScreeningDetailsDto;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {
    List<MovieScreeningsDto> searchForMovieScreenings(@NonNull LocalDateTime start, @NonNull LocalDateTime end);

    ScreeningDetailsDto getScreeningDetails(long screeningId);
}
