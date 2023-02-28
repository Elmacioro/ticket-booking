package com.elmc.booking.domain.screening;

import lombok.NonNull;

public record Room(@NonNull String name, int numberOfRows, int numberOfSeatsInRow) {

    public Room {
        validateParameters(name, numberOfRows, numberOfSeatsInRow);
    }

    private void validateParameters(String name, int numberOfRows, int numberOfSeatsInRow) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("Room name must not be empty");
        }
        if (numberOfRows <=0 || numberOfSeatsInRow <= 0) {
            throw new IllegalArgumentException("Room numberOfRows and numberOfSeatsInRow values have to be bigger than 0");
        }
    }
}
