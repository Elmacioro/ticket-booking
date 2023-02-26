package com.elmc.booking.adapters.incoming.rest;

import java.time.LocalDateTime;

public record ErrorMessage(LocalDateTime dateTime, String description) {
}