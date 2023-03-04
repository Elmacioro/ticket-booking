package com.elmc.booking.adapters.incoming.rest;

import com.elmc.booking.domain.reservation.exceptions.*;
import com.elmc.booking.domain.screening.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidScreeningTimeIntervalExceptionHandler(InvalidScreeningTimeIntervalException exception) {
        log.error("Exception thrown InvalidScreeningTimeIntervalException: {}", exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage("startTime has to be before endTime [startTime: %s], [endTime: %s]"
                        .formatted(exception.getStartTime(), exception.getStartTime()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> tooLongIntervalExceptionHandler(TooLongIntervalException exception) {
        log.error("Exception thrown TooLongIntervalException: {}", exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage("Time interval between two dates can be at max 1 week [startTime: %s], [endTime: %s]"
                        .formatted(exception.getStartTime(), exception.getStartTime()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noSuchScreeningExceptionHandler(NoSuchScreeningException exception) {
        log.error("Exception thrown NoSuchScreeningException: {}", exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage("No screening was found for provided id: [screeningId: %d]"
                .formatted(exception.getScreeningId()));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> seatAlreadyBookedExceptionHandler(SeatAlreadyBookedException exception) {
        log.error("Exception thrown SeatAlreadyBookedException: Provided seats are already booked: [seatIds: {}]",
                exception.getSeatsToBook(), exception);
        ErrorMessage errorMessage = new ErrorMessage("Provided seats have been already booked by another client");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> singleSeatLeftOutAfterBookingExceptionHandler(SingleSeatLeftOutAfterBookingException exception) {
        log.error("Exception thrown SingleSeatLeftOutAfterBookingException: {}", exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage(
                "Cannot book provided seats as one seat would be left out between two booked seats");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidFirstnameExceptionHandler(InvalidFirstnameException exception) {
        log.error("Exception thrown InvalidFirstnameException: {}", exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage(
                "Invalid firstname provided. Firstname must be at least 3 characters long [firstname: %s]"
                        .formatted(exception.getFirstname()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidSurnameExceptionHandler(InvalidSurnameException exception) {
        log.error("Exception thrown InvalidSurnameException: {}", exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage(
                "Invalid surname provided. Surname must be at least 3 characters long [firstname: %s]"
                        .formatted(exception.getSurname()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noTicketsForReservationExceptionHandler(NoTicketsForReservationException exception) {
        log.error("Exception thrown NoTicketsForReservationException: {}", exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage("Reservation must apply to at least one seat");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> sameSeatChosenMultipleTimesExceptionHandler(SameSeatChosenMultipleTimesException exception) {
        log.error("Exception thrown SameSeatChosenMultipleTimesException: Same seat was chosen multiple times [seatIds: {}]",
                exception.getSeatsToBook(), exception);
        ErrorMessage errorMessage = new ErrorMessage("You cannot chose the same seat more than once during booking");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> bookingToLateExceptionHandler(BookingToLateException exception) {
        log.error("Exception thrown BookingToLateException: {}", exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage("Seats can be booked at least %d minutes before screening starts"
                        .formatted(exception.getMinutesToBookBeforeScreening()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> seatsNotExistExceptionHandler(SeatsNotExistException exception) {
        log.error("Exception thrown SeatsNotExistException: Some provided seats do not exist for screening {}",
                exception.getSeatsToBook(), exception);
        ErrorMessage errorMessage = new ErrorMessage("Provided seats do not exist for given screening");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidTicketTypesExceptionHandler(InvalidTicketTypesException exception) {
        log.error("Exception thrown InvalidTicketTypesException: Some provided ticket types do not exist {}",
                exception.getProvidedTicketTypeNames(), exception);
        ErrorMessage errorMessage = new ErrorMessage("Provided ticket types do not exist in the system");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> differentCurrenciesExceptionHandler(DifferentCurrenciesException exception) {
        log.error("Exception thrown DifferentCurrenciesException: {}", exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage(
                        "Only one currency can be used for single reservation [number of provided currencies: %d]"
                                .formatted(exception.getNumberOfCurrencies()));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}