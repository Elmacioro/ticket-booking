package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SearchForPastScreeningsException extends RuntimeException {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public SearchForPastScreeningsException(LocalDateTime startTime, LocalDateTime endTime) {
        super("Cannot search for screenings from the past [startTime: %s, endTime: %s]".formatted(startTime, endTime));
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
