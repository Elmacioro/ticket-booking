package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.domain.ports.dto.MovieScreeningDto;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ScreeningDao implements ScreeningRepository {

    private final ScreeningJpaRepository screeningJpaRepository;

    //TODO
    @Override
    public List<MovieScreeningDto> getMovieScreeningsInDateRange(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        screeningJpaRepository.findScreeningsInDateRange(start, end);
        return null;
    }
    //TODO
    @Override
    public Optional<Screening> findScreeningById(long screeningId) {
        screeningJpaRepository.findScreeningWithReservationsAndTicketsById(screeningId);
        return null;
    }
}
