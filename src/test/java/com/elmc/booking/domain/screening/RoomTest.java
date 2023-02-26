package com.elmc.booking.domain.screening;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @ParameterizedTest
    @NullAndEmptySource
    public void constructorShouldThrowExceptionWhenNameEmptyOrNull(String roomName) {
        assertThrows(RuntimeException.class,
                () -> new Room(roomName, 10, 10));
    }

    @Test
    public void constructorShouldThrowExceptionWhenNameLongerThan50() {
        String tooLongName = "A".repeat(51);

        assertThrows(IllegalArgumentException.class,
                () -> new Room(tooLongName, 10, 10));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenNumberOfRowsLessThan1(int rowsNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("Room A", rowsNumber, 10));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -250})
    public void constructorShouldThrowExceptionWhenNumberOfSeatsInRowLessThan1(int seatsInRowNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("Room A", 10, seatsInRowNumber));
    }

}