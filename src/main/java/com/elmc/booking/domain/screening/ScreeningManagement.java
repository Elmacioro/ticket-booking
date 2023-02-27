package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.ports.dto.outgoing.MovieScreeningsDto;
import com.elmc.booking.domain.ports.dto.outgoing.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.dto.shared.ScreeningTimeDto;
import com.elmc.booking.domain.ports.incoming.ScreeningService;
import com.elmc.booking.domain.ports.outgoing.ScreeningRepository;
import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import com.elmc.booking.domain.screening.exceptions.NoSuchScreeningException;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
public class ScreeningManagement implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    @Override
    public List<MovieScreeningsDto> searchForMovieScreenings(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        validateTimeInterval(start, end);
        return mapToMovieScreenings(screeningRepository.getMovieScreeningsInDateRange(start, end));
    }

    @Override
    public ScreeningDetailsDto getScreeningDetails(long screeningId) {
        return screeningRepository.findScreeningById(screeningId)
                .map(ScreeningDetailsDto::new)
                .orElseThrow(() -> new NoSuchScreeningException(screeningId));
    }

    private void validateTimeInterval(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new InvalidScreeningTimeIntervalException(start, end);
        }
    }

    private List<MovieScreeningsDto> mapToMovieScreenings(List<Screening> screenings) {
        Map<Movie, List<ScreeningTimeDto>> movieScreenings = getMovieScreeningsTimeMap(screenings);
        sortScreeningTimesByStartTime(movieScreenings);
        return convertMovieScreeningTimesToList(movieScreenings);
    }

    private List<MovieScreeningsDto> convertMovieScreeningTimesToList(Map<Movie, List<ScreeningTimeDto>> movieScreenings) {
        return movieScreenings.entrySet()
                .stream()
                .map(entry -> new MovieScreeningsDto(entry.getKey().id(),
                        entry.getKey().title(),
                        entry.getValue()))
                .sorted((Comparator.comparing(MovieScreeningsDto::movieTitle)))
                .toList();
    }

    private void sortScreeningTimesByStartTime(Map<Movie, List<ScreeningTimeDto>> movieScreenings) {
        movieScreenings.forEach((movie, screeningTimes) -> screeningTimes.sort(Comparator.comparing(ScreeningTimeDto::start)));
    }

    private Map<Movie, List<ScreeningTimeDto>> getMovieScreeningsTimeMap(List<Screening> screenings) {
        Map<Movie, List<ScreeningTimeDto>> movieScreenings = new HashMap<>();
        for (Screening screening : screenings) {
            ScreeningTimeDto screeningTimeDto = new ScreeningTimeDto(screening);
            if (!movieScreenings.containsKey(screening.getMovie())) {
                movieScreenings.put(screening.getMovie(), new ArrayList<>(List.of(screeningTimeDto)));
            } else {
                movieScreenings.get(screening.getMovie()).add(screeningTimeDto);
            }
        }
        return movieScreenings;
    }


}
