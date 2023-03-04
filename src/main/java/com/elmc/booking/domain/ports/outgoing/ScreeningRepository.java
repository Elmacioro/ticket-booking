package com.elmc.booking.domain.ports.outgoing;

import com.elmc.booking.domain.screening.Screening;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository {

    List<Screening> getMovieScreeningsInDateRange(@NonNull LocalDateTime start, @NonNull LocalDateTime end);

    Screening getScreeningById(long screeningId);
}
