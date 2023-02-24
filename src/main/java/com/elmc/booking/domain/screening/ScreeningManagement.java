package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.ports.dto.MovieScreeningDto;
import com.elmc.booking.domain.ports.dto.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.incoming.ScreeningService;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import com.elmc.booking.domain.screening.exceptions.NoSuchScreeningException;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class ScreeningManagement implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    @Override
    public List<MovieScreeningDto> searchForMovieScreenings(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        validateTimeInterval(start, end);
        return screeningRepository.getMovieScreeningsInDateRange(start, end);
    }

    @Override
    public ScreeningDetailsDto getScreeningDetails(long screeningId) {
        Screening screening = screeningRepository.findScreeningById(screeningId)
                .orElseThrow(() -> new NoSuchScreeningException(screeningId));
        return new ScreeningDetailsDto(screening);
    }

    private void validateTimeInterval(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new InvalidScreeningTimeIntervalException();
        }
    }

}
