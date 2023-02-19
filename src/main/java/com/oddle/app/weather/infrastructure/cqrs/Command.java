package com.oddle.app.weather.infrastructure.cqrs;

import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.config
 */
public interface Command<I, O> {

    void validate(I request);

    O process(I request);

    @SneakyThrows
    @Transactional
    default O execute(I request) {
        validate(request);
        return process(request);
    }
}