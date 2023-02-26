package com.elmc.booking.domain.screening;

import org.junit.jupiter.api.Test;
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

    @Test
    void constructorShouldThrowExceptionWhenTitleLongerThan250() {
        String tooLongTitle = "A".repeat(251);

        assertThrows(IllegalArgumentException.class,
                () -> new Movie(1, tooLongTitle, "SomeDesc"));
    }


    @ParameterizedTest
    @NullAndEmptySource
    void constructorShouldThrowExceptionWhenDescriptionEmptyOrNull(String description) {
        assertThrows(RuntimeException.class,
                () -> new Movie(1, "Django", description));
    }

    @Test
    void constructorShouldThrowExceptionWhenDescriptionLongerThan1500() {
        String tooLongDescription = "A".repeat(1501);

        assertThrows(IllegalArgumentException.class,
                () -> new Movie(1, "Django", tooLongDescription));
    }

}