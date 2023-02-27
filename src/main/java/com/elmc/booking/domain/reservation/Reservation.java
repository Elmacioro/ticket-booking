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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
public class Reservation {

    public static final String FIRSTNAME_REGEX = "^[A-Z][a-zżźćńółęąś]{2,}$";

    public static final String SURNAME_REGEX = "^[A-Z][a-zżźćńółęąś]{2,}(-[A-Z][a-zżźćńółęąś]{2,})?$";

    private Long reservationId;

    private final long screeningId;

    private final List<Ticket> tickets;

    private final String firstname;

    private final String surname;

    private final LocalDateTime expirationDate;

    private boolean isPaid = false;

    public Reservation(Long reservationId,
                       long screeningId,
                       @NonNull List<Ticket> tickets,
                       @NonNull String firstname,
                       @NonNull String surname) {
        validateTickets(tickets);
        this.firstname = getValidFirstname(firstname);
        this.surname = getValidSurname(surname);
        this.reservationId = reservationId;
        this.screeningId = screeningId;
        this.tickets = tickets;
        this.expirationDate = LocalDateTime.now().plusMinutes(5);
    }

    public Reservation(long screeningId,
                       List<Ticket> tickets,
                       String firstname,
                       String surname) {
        this(null, screeningId, tickets, firstname, surname);
    }

    public Price calculateTotalPrice() {
        BigDecimal sum = tickets.stream()
                .map(ticket -> ticket.ticketType().price().amount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String currency = tickets.get(0).ticketType().price().currency();
        return new Price(sum, currency);
    }

    private String getValidFirstname(String firstname) {
        firstname = formatName(firstname);
        if (!isFirstnameValid(firstname)) {
            throw new InvalidFirstnameException(firstname);
        }
        return firstname;
    }

    private String getValidSurname(String surname) {
        surname = formatSurname(surname);
        if (!isSurnameValid(surname)) {
            throw new InvalidSurnameException(surname);
        }
        return surname;
    }

    private String formatName(String firstname) {
        firstname = firstname.trim();
        firstname = capitalizeFirstLetter(firstname);
        return firstname;
    }

    private String capitalizeFirstLetter(String name) {
        return name
                .substring(0, 1)
                .toUpperCase() + name.substring(1);
    }

    private boolean isFirstnameValid(String firstname) {
        return firstname.matches(FIRSTNAME_REGEX);
    }

    private boolean isSurnameValid(String surname) {
        return surname.matches(SURNAME_REGEX);

    }

    private String formatSurname(String surname) {
        surname = formatName(surname);
        if (isTwoPartSurname(surname)) {
            String[] parts = surname.split("-");
            parts[1] = capitalizeFirstLetter(parts[1]);
            surname =  parts[0] + "-" + parts[1];
        }
        return surname;
    }

    private boolean isTwoPartSurname(String surname) {
        Pattern pattern = Pattern.compile("-");
        Matcher matcher = pattern.matcher(surname);
        return matcher.matches();
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

}
