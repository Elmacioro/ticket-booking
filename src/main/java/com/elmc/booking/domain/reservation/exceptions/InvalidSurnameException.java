package com.elmc.booking.domain.reservation.exceptions;

import lombok.Getter;

@Getter
public class InvalidSurnameException extends RuntimeException {

    private final String surname;

    public InvalidSurnameException(String surname) {
        super("Provided surname is invalid: [surname: %s]".formatted(surname));
        this.surname = surname;
    }
}
