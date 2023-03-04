package com.elmc.booking.domain.screening;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SeatIdTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenRowNumberLessThan1(int rowNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new SeatId(rowNumber, 10));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenSeatNumberLessThan1(int seatNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new SeatId(10, seatNumber));
    }

}