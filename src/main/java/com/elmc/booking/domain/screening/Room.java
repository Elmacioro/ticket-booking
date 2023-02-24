package com.elmc.booking.domain.screening;

import lombok.NonNull;

public record Room(@NonNull String name, int numberOfRows, int numberOfSeatsInRow) {
    public Room {
        validateParameters(name, numberOfRows, numberOfSeatsInRow);
    }

    private void validateParameters(String name, int numberOfRows, int numberOfSeatsInRow) {
        if(name.length() == 0 || name.length() > 50) {
            throw new IllegalArgumentException("Room name has to be between 1 and 50 characters long");
        }
        if (numberOfRows <=0 || numberOfSeatsInRow <=0) {
            throw new IllegalArgumentException("Room numberOfRows and numberOfSeatsInRow values have to be bigger than 0");
        }
    }
}
