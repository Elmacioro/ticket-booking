package com.elmc.booking.domain.reservation.exceptions;

import lombok.Getter;

@Getter
public class DifferentCurrenciesException extends RuntimeException {

    private final int numberOfCurrencies;

    public DifferentCurrenciesException(int numberOfCurrencies) {
        super("Only one currency can be used for single reservation");
        this.numberOfCurrencies = numberOfCurrencies;
    }
}
