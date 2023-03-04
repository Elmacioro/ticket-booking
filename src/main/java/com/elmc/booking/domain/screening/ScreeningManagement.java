package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.ports.dto.outgoing.MovieScreeningsDto;
import com.elmc.booking.domain.ports.dto.outgoing.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.incoming.ScreeningService;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ScreeningManagement implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    @Override
    public List<MovieScreeningsDto> searchForMovieScreenings(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        validateTimeInterval(start, end);
        List<Screening> screenings = screeningRepository.getMovieScreeningsInDateRange(start, end);
        return getSortedMoviesScreeningsTimes(screenings);
    }

    @Override
    public ScreeningDetailsDto getScreeningDetails(long screeningId) {
        Screening screening = screeningRepository.getScreeningById(screeningId);
        return new ScreeningDetailsDto(screening);
    }

    private void validateTimeInterval(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new InvalidScreeningTimeIntervalException(start, end);
        }
    }

    private List<MovieScreeningsDto> getSortedMoviesScreeningsTimes(List<Screening> screenings) {
        return screenings.stream()
                .sorted(Comparator.comparing(Screening::getStartTime))
                .collect(Collectors.groupingBy(Screening::getMovie))
                .entrySet().stream()
                .map(entry -> new MovieScreeningsDto(entry.getKey(),
                        entry.getValue()))
                .sorted(Comparator.comparing(MovieScreeningsDto::getMovieTitle))
                .toList();
    }

}
