package com.elmc.booking.domain.screening;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @ParameterizedTest
    @NullAndEmptySource
    void constructorShouldThrowExceptionWhenTitleEmptyOrNull(String title) {
        assertThrows(RuntimeException.class,
                () -> new Movie(1, title, "SomeDesc"));
    }


    @ParameterizedTest
    @NullAndEmptySource
    void constructorShouldThrowExceptionWhenDescriptionEmptyOrNull(String description) {
        assertThrows(RuntimeException.class,
                () -> new Movie(1, "Django", description));
    }

}