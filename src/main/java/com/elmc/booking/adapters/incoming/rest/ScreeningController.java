package com.elmc.booking.adapters.incoming.rest;

import com.elmc.booking.domain.ports.dto.outgoing.MovieScreeningsDto;
import com.elmc.booking.domain.ports.dto.outgoing.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.incoming.ScreeningService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/screening")
@RestController
@Slf4j
@AllArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MovieScreeningsDto> searchForScreenings(@RequestParam("start")
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       LocalDateTime start,
                                                        @RequestParam("end")
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       LocalDateTime end) {
        log.debug("Search for movie screenings received [startTime: {}], [endTime: {}]", start, end);
        return screeningService.searchForMovieScreenings(start, end);
    }

    @GetMapping("/{screeningId}")
    @ResponseStatus(HttpStatus.OK)
    public ScreeningDetailsDto getScreeningDetails(@PathVariable UUID screeningId) {
        log.debug("Request for screening details received [screeningId: {}]", screeningId);
        return screeningService.getScreeningDetails(screeningId);
    }

}
