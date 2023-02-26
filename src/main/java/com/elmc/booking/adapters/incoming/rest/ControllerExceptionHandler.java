package com.elmc.booking.adapters.incoming.rest;

import com.elmc.booking.domain.screening.exceptions.InvalidScreeningTimeIntervalException;
import com.elmc.booking.domain.screening.exceptions.NoSuchScreeningException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidScreeningTimeIntervalExceptionHandler(InvalidScreeningTimeIntervalException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                String.format("""
                        startTime has to be before endTime
                        [startTime: %s], [endTime: %s]
                        """,
                        exception.getStartTime(),
                        exception.getStartTime()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> NoSuchScreeningExceptionHandler(NoSuchScreeningException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                String.format("""
                        No screening was found for provided id:
                        [screeningId: %d]
                        """,
                        exception.getScreeningId()));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> GeneralExceptionHandler(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}