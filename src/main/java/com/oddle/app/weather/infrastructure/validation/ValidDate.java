package com.oddle.app.weather.infrastructure.validation;

import com.oddle.app.weather.infrastructure.validation.validator.ValidDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.validation
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
public @interface ValidDate {

    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    String message() default "field.not.valid.date.format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean optional() default false;

    String format() default DATE_FORMAT;

}
