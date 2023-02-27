package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.adapters.outgoing.database.repository.ScreeningJpaRepository;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.Screening;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Transactional
public class ScreeningDao implements ScreeningRepository {

    private final ScreeningJpaRepository screeningJpaRepository;
    private final EntityToDomainMapper entityToDomainMapper;

    @Override
    public List<Screening> getMovieScreeningsInDateRange(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        return screeningJpaRepository.findScreeningsInDateRange(start, end)
                .stream()
                .map(entityToDomainMapper::mapToDomain)
                .toList();
    }

    @Override
    public Optional<Screening> findScreeningById(long screeningId) {
        return screeningJpaRepository.findScreeningWithReservationsAndTicketsById(screeningId)
                .map(entityToDomainMapper::mapToDomain);
    }
}
