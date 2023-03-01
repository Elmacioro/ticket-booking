package com.elmc.booking.domain.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    private TicketType ticketType;

    @BeforeEach
    void setUp() {
        Price price = new Price(new BigDecimal(25), "PLN");
        ticketType = new TicketType(1, "adult", price);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenRowNumberLessThan1(int rowNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new Ticket(rowNumber, 1, ticketType));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenSeatInRowNumberLessThan1(int seatInRowNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new Ticket(1, seatInRowNumber, ticketType));
    }

}