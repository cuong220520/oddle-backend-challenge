package com.oddle.app.weather.infrastructure.logger;

import com.oddle.app.weather.infrastructure.builder.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.logger
 */
@Aspect
@Component
@Slf4j
public class LogExecutionTimeAspect {

    @Around("@annotation(com.oddle.app.weather.infrastructure.logger.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final StopWatch STOP_WATCH = new StopWatch();

        STOP_WATCH.start();

        Object proceed = joinPoint.proceed();

        STOP_WATCH.stop();

        log.info("<<< method: {} executed in {} ms >>> ",
                joinPoint.getSignature().getName(),
                STOP_WATCH.getLastTaskTimeMillis());

        return proceed;
    }

}
