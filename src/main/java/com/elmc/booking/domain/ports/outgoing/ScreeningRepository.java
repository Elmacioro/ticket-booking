package com.elmc.booking.domain.ports.outgoing;

import com.elmc.booking.domain.screening.Screening;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningRepository {

    List<Screening> getMovieScreeningsInDateRange(@NonNull LocalDateTime start, @NonNull LocalDateTime end);

    Optional<Screening> findScreeningById(long screeningId);
}
