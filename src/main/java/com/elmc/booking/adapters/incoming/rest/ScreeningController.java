package com.elmc.booking.adapters.incoming.rest;

import com.elmc.booking.domain.ports.dto.MovieScreeningDto;
import com.elmc.booking.domain.ports.dto.ScreeningDetailsDto;
import com.elmc.booking.domain.ports.incoming.ScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/screening")
@RestController
@AllArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping
    public List<MovieScreeningDto> searchForScreenings(@RequestParam("start")
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       LocalDateTime start,
                                                       @RequestParam("end")
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       LocalDateTime end) {
        return screeningService.searchForMovieScreenings(start, end);
    }

    @GetMapping("/{id}")
    public ScreeningDetailsDto getScreeningDetails(@PathVariable long id) {
        return screeningService.getScreeningDetails(id);
    }

}
