package com.elmc.booking.domain.screening.exceptions;

import java.time.LocalDateTime;

public class InvalidScreeningTimeIntervalException extends RuntimeException {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public InvalidScreeningTimeIntervalException(LocalDateTime startTime, LocalDateTime endTime) {
        super("Screening startTime has to be before endTime");
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
