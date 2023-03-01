package com.elmc.booking.domain.reservation;

import com.elmc.booking.domain.reservation.exceptions.DifferentCurrenciesException;
import com.elmc.booking.domain.reservation.exceptions.InvalidFirstnameException;
import com.elmc.booking.domain.reservation.exceptions.InvalidSurnameException;
import com.elmc.booking.domain.reservation.exceptions.NoTicketsForReservationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private final long screeningId = 1;
    private String firstname;
    private String surname;
    private List<Ticket> tickets;

    @BeforeEach
    void setUp() {
        firstname = "Łucja";
        surname = "Kowalska";
        tickets = prepareTValidTickets();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ed", "Ad@m", "__John", "Fred.", "karol"})
    void constructorShouldThrowExceptionForInvalidFirstname(String invalidFirstname) {
        assertThrows(InvalidFirstnameException.class,
                () -> new Reservation(screeningId, tickets, invalidFirstname, surname));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Łucja", "Adam"})
    void constructorShouldInitializeProperlyWithFirstname(String firstname) {
        Reservation reservation = new Reservation(screeningId, tickets, firstname, surname);
        assertEquals(firstname, reservation.getFirstname());
    }

    @ParameterizedTest
    @ValueSource(strings = {"kowalski", "Ko", "Kowalski-Su", "Kowal-nowak", "Kow@laski", "Nowak-Kow@lski"})
    void constructorShouldThrowExceptionForInvalidSurname(String invalidSurname) {
        assertThrows(InvalidSurnameException.class,
                () -> new Reservation(screeningId, tickets, firstname, invalidSurname));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Łęcka", "Nowak", "Nowak-Łęcka"})
    void constructorShouldInitializeProperlyWithSurname(String validSurname) {
        Reservation reservation = new Reservation(screeningId, tickets, firstname, validSurname);
        assertEquals(validSurname, reservation.getSurname());
    }

    @Test
    void constructorShouldThrowExceptionForNoTickets() {
        assertThrows(NoTicketsForReservationException.class,
                () -> new Reservation(screeningId, new ArrayList<>(), firstname, surname));
    }

    @Test
    void constructorShouldThrowExceptionForTicketsWithDifferentCurrencies() {
        List<Ticket> invalidTickets = prepareInvalidTicketsWithDifferentCurrencies();
        assertThrows(DifferentCurrenciesException.class,
                () -> new Reservation(screeningId, invalidTickets, firstname, surname));
    }

    @Test
    void calculateTotalPriceShouldProperlyCalculateTotalPrice() {
        Reservation reservation = new Reservation(screeningId, tickets, firstname, surname);

        Price actualPrice = reservation.calculateTotalPrice();

        assertTrue(BigDecimal.valueOf(55.5).compareTo(actualPrice.amount()) == 0);
        assertEquals("PLN", actualPrice.currency());
    }

    private List<Ticket> prepareInvalidTicketsWithDifferentCurrencies() {
        TicketType adult = new TicketType(1, "adult", new Price(BigDecimal.valueOf(25), "PLN"));
        TicketType child = new TicketType(2, "child", new Price(BigDecimal.valueOf(12.5), "USD"));

        return List.of(
                new Ticket(1, 1, adult),
                new Ticket(1, 2, child));
    }


    private List<Ticket> prepareTValidTickets() {
        TicketType adult = new TicketType(1, "adult", new Price(BigDecimal.valueOf(25), "PLN"));
        TicketType child = new TicketType(2, "child", new Price(BigDecimal.valueOf(12.5), "PLN"));
        TicketType student = new TicketType(3, "student", new Price(BigDecimal.valueOf(18), "PLN"));

        return List.of(
                new Ticket(1, 1, adult),
                new Ticket(1, 2, child),
                new Ticket(1, 3, student));
    }
}