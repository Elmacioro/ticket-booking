package com.elmc.booking.domain.screening;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void constructorShouldThrowExceptionWhenTitleBlank(String title) {
        assertThrows(IllegalArgumentException.class,
                () -> new Movie(1, title, "SomeDesc"));
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void constructorShouldThrowExceptionWhenDescriptionBlank(String description) {
        assertThrows(IllegalArgumentException.class,
                () -> new Movie(1, "Django", description));
    }

}
