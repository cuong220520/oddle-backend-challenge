package com.oddle.app.weather.infrastructure.validation;

import com.oddle.app.weather.infrastructure.validation.validator.BelongValidator;

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
@Constraint(validatedBy = BelongValidator.class)
public @interface Belong {

    String message() default "field.not.belong_to";

    boolean optional() default false;

    Class<? extends Enum<?>> enumClazz();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
