package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.ports.dto.outgoing.MovieScreeningsDto;
import com.elmc.booking.domain.ports.dto.outgoing.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.incoming.ScreeningService;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import com.elmc.booking.domain.screening.exceptions.SearchForPastScreeningsException;
import com.elmc.booking.domain.screening.exceptions.TooLongIntervalException;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ScreeningManagement implements ScreeningService {

    @NonNull
    private final ScreeningRepository screeningRepository;

    @Override
    public List<MovieScreeningsDto> searchForMovieScreenings(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        validateTimeInterval(start, end);
        List<Screening> screenings = screeningRepository.getMovieScreeningsInDateRange(start, end);
        return getSortedByMovieTitleAndScreeningTimes(screenings);
    }

    @Override
    public ScreeningDetailsDto getScreeningDetails(@NonNull UUID screeningId) {
        Screening screening = screeningRepository.getScreeningById(screeningId);
        return new ScreeningDetailsDto(screening);
    }

    private void validateTimeInterval(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new InvalidScreeningTimeIntervalException(start, end);
        }
        if (start.isBefore(LocalDateTime.now())) {
            throw new SearchForPastScreeningsException(start, end);
        }
        if (start.plusWeeks(1).isBefore(end)) {
            throw new TooLongIntervalException(start, end);
        }
    }

    private List<MovieScreeningsDto> getSortedByMovieTitleAndScreeningTimes(List<Screening> screenings) {
        List<Screening> screeningSortedByStartTime = sortScreeningsByStartTime(screenings);
        List<MovieScreeningsDto> groupedScreenings  = groupScreeningsByMovies(screeningSortedByStartTime);
        return sortByMovieTitle(groupedScreenings);
    }

    private List<Screening> sortScreeningsByStartTime(List<Screening> screenings) {
        return screenings.stream()
                .sorted(Comparator.comparing(Screening::getStartTime))
                .toList();
    }

    private List<MovieScreeningsDto> groupScreeningsByMovies(List<Screening> screenings) {
        return screenings.stream()
                .collect(Collectors.groupingBy(Screening::getMovie))
                .entrySet().stream()
                .map(entry -> new MovieScreeningsDto(entry.getKey(), entry.getValue()))
                .toList();
    }

    private List<MovieScreeningsDto> sortByMovieTitle(List<MovieScreeningsDto> movieScreeningsDtos) {
        return movieScreeningsDtos.stream()
                .sorted(Comparator.comparing(MovieScreeningsDto::getMovieTitle))
                .toList();
    }

}
