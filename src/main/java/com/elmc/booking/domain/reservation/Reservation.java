package com.elmc.booking.domain.reservation;

import com.elmc.booking.domain.reservation.exceptions.DifferentCurrenciesException;
import com.elmc.booking.domain.reservation.exceptions.InvalidFirstnameException;
import com.elmc.booking.domain.reservation.exceptions.InvalidSurnameException;
import com.elmc.booking.domain.reservation.exceptions.NoTicketsForReservationException;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class Reservation {

    private static final String FIRSTNAME_REGEX = "^\\p{IsUppercase}\\p{IsLowercase}{2,}$";

    private static final String SURNAME_REGEX = "^\\p{IsUppercase}\\p{IsLowercase}{2,}(-\\p{IsUppercase}\\p{IsLowercase}{2,})?$";

    private static final int MINUTES_TO_EXPIRE = 5;

    private final UUID reservationId;

    private final UUID screeningId;

    private final List<Ticket> tickets;

    private final String firstname;

    private final String surname;

    private final LocalDateTime expirationDate;

    private final boolean isPaid = false;

    public Reservation(@NonNull UUID screeningId,
                       @NonNull List<Ticket> tickets,
                       @NonNull String firstname,
                       @NonNull String surname) {

        validateParameters(tickets, firstname, surname);
        this.firstname = firstname;
        this.surname = surname;
        this.reservationId = UUID.randomUUID();
        this.screeningId = screeningId;
        this.tickets = tickets;
        this.expirationDate = LocalDateTime.now().plusMinutes(MINUTES_TO_EXPIRE);
    }

    public Price calculateTotalPrice() {
        BigDecimal sum = tickets.stream()
                .map(ticket -> ticket.ticketType().price().amount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // There has to be at least 1 ticket for reservation and all of them have to use the same currency
        String currency = tickets.get(0).ticketType().price().currency();
        return new Price(sum, currency);
    }

    private void validateParameters(List<Ticket> tickets, String firstname, String surname) {
        validateTickets(tickets);
        validateFirstname(firstname);
        validateSurname(surname);
    }

    private void validateTickets(List<Ticket> tickets) {
        if (tickets.size() < 1) {
            throw new NoTicketsForReservationException();
        }
        int numberOfDifferentCurrencies = tickets.stream()
                .map(ticket -> ticket.ticketType().price().currency())
                .collect(Collectors.toSet())
                .size();
        if (numberOfDifferentCurrencies != 1) {
            throw new DifferentCurrenciesException(numberOfDifferentCurrencies);
        }
    }

    private void validateFirstname(String firstname) {
        if (!isFirstnameValid(firstname)) {
            throw new InvalidFirstnameException(firstname);
        }
    }

    private void validateSurname(String surname) {
        if (!isSurnameValid(surname)) {
            throw new InvalidSurnameException(surname);
        }
    }

    private boolean isFirstnameValid(String firstname) {
        return firstname.matches(FIRSTNAME_REGEX);
    }

    private boolean isSurnameValid(String surname) {
        return surname.matches(SURNAME_REGEX);

    }

}
