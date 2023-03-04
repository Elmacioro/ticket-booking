package com.elmc.booking.domain.screening;

import lombok.NonNull;

public record Room(@NonNull String name, int rowsNumber, int seatsInRowNumber) {

    public Room {
        validateParameters(name, rowsNumber, seatsInRowNumber);
    }

    private void validateParameters(String name, int rowsNumber, int seatsInRowNumber) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("Room name must not be empty");
        }
        if (rowsNumber <= 0 || seatsInRowNumber <= 0) {
            throw new IllegalArgumentException("Room rowsNumber and seatsInRowNumber values have to be bigger than 0");
        }
    }
}
