package com.oddle.app.weather.infrastructure.validation.validator;

import com.oddle.app.weather.infrastructure.validation.Belong;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.validation
 */
public class BelongValidator implements ConstraintValidator<Belong, String> {

    Class<? extends Enum<?>> enumClazz;
    private boolean optional;

    @Override
    public void initialize(Belong constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.enumClazz = constraintAnnotation.enumClazz();
        this.optional = constraintAnnotation.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        List<String> acceptableValues = Arrays.stream(enumClazz.getEnumConstants()).map(Enum::name).collect(Collectors.toList());

        return optional
                || !StringUtils.hasText(value)
                || ArrayUtils.isEmpty(enumClazz.getEnumConstants())
                || acceptableValues.contains(value.trim())
                ;
    }


}
