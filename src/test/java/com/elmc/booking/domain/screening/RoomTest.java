package com.elmc.booking.domain.screening;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    public void constructorShouldThrowExceptionWhenNameBlank(String roomName) {
        assertThrows(IllegalArgumentException.class,
                () -> new Room(roomName, 10, 10));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenRowsNumberLessThan1(int rowsNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("Room A", rowsNumber, 10));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenSeatsInRowNumberLessThan1(int seatsInRowNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("Room A", 10, seatsInRowNumber));
    }

}