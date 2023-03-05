package com.elmc.booking.adapters.outgoing.database.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = LocalDateTimeRangeValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface ValidLocalDateTimeRange {

    String message() default "Start time must be before end time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String start();

    String end();
}
