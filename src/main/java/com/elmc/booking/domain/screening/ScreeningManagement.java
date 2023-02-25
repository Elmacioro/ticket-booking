package com.elmc.booking.domain.screening;

import com.elmc.booking.domain.ports.dto.MovieScreeningDto;
import com.elmc.booking.domain.ports.dto.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.dto.ScreeningTime;
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
    public List<MovieScreeningDto> searchForMovieScreenings(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
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
            throw new InvalidScreeningTimeIntervalException();
        }
    }

    private List<MovieScreeningDto> mapToMovieScreenings(List<Screening> screenings) {
        Map<Movie, List<ScreeningTime>> movieScreenings = getMovieScreeningsTimeMap(screenings);
        sortScreeningTimesByStartTime(movieScreenings);
        return convertMovieScreeningTimesToList(movieScreenings);
    }

    private List<MovieScreeningDto> convertMovieScreeningTimesToList(Map<Movie, List<ScreeningTime>> movieScreenings) {
        return movieScreenings.entrySet()
                .stream()
                .map(entry -> new MovieScreeningDto(entry.getKey().id(),
                        entry.getKey().title(),
                        entry.getValue()))
                .sorted((Comparator.comparing(MovieScreeningDto::movieTitle)))
                .toList();
    }

    private void sortScreeningTimesByStartTime(Map<Movie, List<ScreeningTime>> movieScreenings) {
        movieScreenings.forEach((movie, screeningTimes) -> screeningTimes.sort(Comparator.comparing(ScreeningTime::start)));
    }

    private Map<Movie, List<ScreeningTime>> getMovieScreeningsTimeMap(List<Screening> screenings) {
        Map<Movie, List<ScreeningTime>> movieScreenings = new HashMap<>();
        for (Screening screening : screenings) {
            ScreeningTime screeningTime = new ScreeningTime(screening);
            if (!movieScreenings.containsKey(screening.getMovie())) {
                movieScreenings.put(screening.getMovie(), new ArrayList<>(List.of(screeningTime)));
            } else {
                movieScreenings.get(screening.getMovie()).add(screeningTime);
            }
        }
        return movieScreenings;
    }


}
