package com.elmc.booking.domain.reservation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TicketTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void constructorShouldThrowExceptionWhenNameBlank(String ticketTypeName) {
        assertThrows(IllegalArgumentException.class,
                () -> new TicketType(1, ticketTypeName, new Price(new BigDecimal(10), "PLN")));
    }
}