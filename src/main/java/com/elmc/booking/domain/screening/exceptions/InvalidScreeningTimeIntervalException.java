package com.elmc.booking.domain.screening.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InvalidScreeningTimeIntervalException extends RuntimeException {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public InvalidScreeningTimeIntervalException(LocalDateTime startTime, LocalDateTime endTime) {
        super("endTime cannot be before startTime [startTime: %s, endTime: %s]".formatted(startTime, endTime));
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
