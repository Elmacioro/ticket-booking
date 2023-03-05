package com.elmc.booking.domain.reservation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {


    @Test
    void constructorShouldThrowExceptionWhenAmountLessThan0() {
        BigDecimal amount = new BigDecimal(-10);
        assertThrows(IllegalArgumentException.class,
                () -> new Price(amount, "PLN"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"$", "zł", ""})
    void constructorShouldThrowExceptionWhenCurrencyCodeLengthIsNot3(String currency) {
        assertThrows(IllegalArgumentException.class,
                () -> new Price(new BigDecimal(10), currency));
    }
}
