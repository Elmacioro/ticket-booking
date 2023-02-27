package com.elmc.booking.domain.reservation.exceptions;

import lombok.Getter;

@Getter
public class InvalidFirstnameException extends RuntimeException {

    private final String firstname;

    public InvalidFirstnameException(String firstname) {
        super(String.format("Invalid firstname provided: [firstname: %s]", firstname));
        this.firstname = firstname;
    }
}
