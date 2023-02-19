package com.oddle.app.weather.infrastructure.cqrs;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.config
 */
public interface Query<I, O>  {

    void validate(I request);

    O process(I request);

    @SneakyThrows
    default O execute(I request) {
        validate(request);
        return process(request);
    }

}
