package com.elmc.booking.domain.screening;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SeatTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenRowNumberLessThan1(int rowNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new Seat(rowNumber, 1, SeatStatus.FREE));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenSeatInRowNumberLessThan1(int seatInRowNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new Seat(1, seatInRowNumber, SeatStatus.FREE));
    }

}