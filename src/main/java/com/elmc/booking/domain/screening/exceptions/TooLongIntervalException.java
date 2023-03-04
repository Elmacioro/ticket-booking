package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TooLongIntervalException extends RuntimeException {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public TooLongIntervalException(LocalDateTime startTime, LocalDateTime endTime) {
        super("Time interval between startTime and endTime is over 1 week [startTime: %s, endTime: %s]".formatted(startTime, endTime));
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
