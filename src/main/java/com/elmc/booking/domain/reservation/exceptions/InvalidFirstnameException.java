package com.elmc.booking.domain.reservation.exceptions;

import lombok.Getter;

@Getter
public class InvalidFirstnameException extends RuntimeException {

    private final String firstname;

    public InvalidFirstnameException(String firstname) {
        super("Invalid firstname provided: [firstname: %s]".formatted(firstname));
        this.firstname = firstname;
    }
}
