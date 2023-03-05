package com.elmc.booking.adapters.outgoing.database.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class LocalDateTimeRangeValidator implements ConstraintValidator<ValidLocalDateTimeRange, Object> {

    private String startFieldName;
    private String endFieldName;

    @Override
    public void initialize(ValidLocalDateTimeRange constraintAnnotation) {
        this.startFieldName = constraintAnnotation.start();
        this.endFieldName = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Field startField = object.getClass().getField(startFieldName);
            startField.setAccessible(true);
            Field endField = object.getClass().getField(endFieldName);
            endField.setAccessible(true);

            LocalDateTime start = (LocalDateTime) startField.get(object);
            LocalDateTime end = (LocalDateTime) endField.get(object);
            return start.isBefore(end);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new LocalDateTimeValidationException(e);
        }
    }
}
