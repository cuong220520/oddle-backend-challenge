package com.oddle.app.weather.infrastructure.validation.validator;

import com.oddle.app.weather.infrastructure.validation.ValidDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.validation
 */
public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {

    private Boolean isOptional;
    private String format;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.isOptional = constraintAnnotation.optional();
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean validDate = isValidFormat(value);

        return isOptional ? (validDate || !StringUtils.hasText(value)) : validDate;
    }

    private boolean isValidFormat(String value) {
        try {
            if (!StringUtils.hasText(value)) return false;

            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(this.format);

            dateTimeFormatter.parseDateTime(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
