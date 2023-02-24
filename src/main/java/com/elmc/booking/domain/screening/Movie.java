package com.elmc.booking.domain.screening;

import lombok.NonNull;

public record Movie(@NonNull String title, @NonNull String description) {
    public Movie {
        validateParameters(title, description);
    }

    private void validateParameters(String title, String description) {
        if (title.length() == 0 || title.length() > 250) {
            throw new IllegalArgumentException("Movie title must be between 1 and 250 characters long");
        }
        if (description.length() == 0 || description.length() > 1500) {
            throw new IllegalArgumentException("Movie description must be between 0 and 1500 characters long");
        }
    }
}
