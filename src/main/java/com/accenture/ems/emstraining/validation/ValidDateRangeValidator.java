package com.accenture.ems.emstraining.validation;

import com.accenture.ems.emstraining.model.dto.TrainingDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, TrainingDTO> {

    @Override
    public boolean isValid(TrainingDTO value, ConstraintValidatorContext context) {
        if (value == null || value.getStartDate() == null || value.getEndDate() == null) {
            return true;
        }

        boolean isValid = value
                .getEndDate()
                .isAfter(value.getStartDate());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Invalid date range")
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
        }
        return isValid;
    }
}