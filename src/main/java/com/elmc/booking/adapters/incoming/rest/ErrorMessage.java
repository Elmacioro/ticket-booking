package com.elmc.booking.adapters.incoming.rest;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorMessage {
    LocalDateTime dateTime = LocalDateTime.now();
    String description;
}
