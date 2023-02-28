package com.elmc.booking.domain.screening;

import lombok.NonNull;

public record Movie(long id, @NonNull String title, @NonNull String description) {

    public Movie {
        validateParameters(title, description);
    }

    private void validateParameters(String title, String description) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Movie title must not be empty");
        }
        if (description.isBlank()) {
            throw new IllegalArgumentException("Movie description must not be empty");
        }
    }
}
