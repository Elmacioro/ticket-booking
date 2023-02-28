package com.elmc.booking.adapters.incoming.rest;

import com.elmc.booking.domain.reservation.exceptions.*;
import com.elmc.booking.domain.screening.exceptions.*;
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
                String.format("startTime has to be before endTime [startTime: %s], [endTime: %s]",
                        exception.getStartTime(),
                        exception.getStartTime()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noSuchScreeningExceptionHandler(NoSuchScreeningException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                String.format("No screening was found for provided id: [screeningId: %d]",
                        exception.getScreeningId()));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> seatAlreadyBookedExceptionHandler(SeatAlreadyBookedException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                "Provided seats have been already booked by another client");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> singleSeatLeftOutAfterBookingExceptionHandler(SingleSeatLeftOutAfterBookingException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                "Cannot book provided seats as one seat would be left out between two booked seats");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidFirstnameExceptionHandler(InvalidFirstnameException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), String.format(
                "Invalid firstname provided. Firstname must be at least 3 characters long [firstname: %s]",
                exception.getFirstname()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidSurnameExceptionHandler(InvalidSurnameException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), String.format(
                "Invalid surname provided. Surname must be at least 3 characters long [firstname: %s]",
                exception.getSurname()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noTicketsForReservationExceptionHandler(NoTicketsForReservationException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                "Reservation must apply to at least one seat");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> sameSeatChosenMultipleTimesExceptionHandler(SameSeatChosenMultipleTimesException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                "You cannot chose the same seat more than once during booking");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> bookingToLateExceptionHandler(BookingToLateException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                String.format("Seats can be booked at least %d minutes before screening starts",
                        exception.getMinutesToBookBeforeScreening()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidSeatsExceptionHandler(InvalidSeatsException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                "Provided seats do not exist for given screening");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidTicketTypesExceptionHandler(InvalidTicketTypesException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                "Provided ticket types do not exist in the system");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> differentCurrenciesExceptionHandler(DifferentCurrenciesException exception) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                String.format(
                        "Only one currency can be used for single reservation [number of provided currencies: %d]",
                        exception.getNumberOfCurrencies()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}