package com.elmc.booking.adapters.outgoing.database.dao;

import com.elmc.booking.adapters.outgoing.database.dao.mapper.EntityToDomainMapper;
import com.elmc.booking.adapters.outgoing.database.repository.ScreeningJpaRepository;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.Screening;
import com.elmc.booking.domain.screening.exceptions.NoSuchScreeningException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
@Slf4j
@Transactional
public class ScreeningDao implements ScreeningRepository {

    private final ScreeningJpaRepository screeningJpaRepository;
    private final EntityToDomainMapper entityToDomainMapper;

    @Override
    public List<Screening> getMovieScreeningsInDateRange(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        log.debug("Fetching for screenings with startTime between {} and {}", start, end);
        return screeningJpaRepository.findScreeningsInDateRange(start, end)
                .stream()
                .map(entityToDomainMapper::map)
                .toList();
    }

    @Override
    public Screening getScreeningById(UUID screeningId) {
        log.debug("Fetching for screening with id: {}", screeningId);
        return screeningJpaRepository.findScreeningWithReservationsAndTicketsById(screeningId)
                .map(entityToDomainMapper::map)
                .orElseThrow(() -> new NoSuchScreeningException(screeningId));
    }
}
