package com.oddle.app.weather.infrastructure.logger;

import com.oddle.app.weather.infrastructure.builder.Gson;
import com.oddle.app.weather.infrastructure.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.log
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RestControllerAspect {

    private final HttpServletRequest httpServletRequest;

    private static final String GET_PARAMS = "queryString";

    @Pointcut("within(com.oddle.app.weather.application.controller..*)")
    public void pointcut() {
    }

    // before method execution
    @Before("pointcut()")
    @SneakyThrows
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String method = signature.getMethod().getName();

        Map<String, String> params;

        if (HttpMethod.GET.matches(httpServletRequest.getMethod())) {
            params = Map.of(
                    GET_PARAMS, StringUtils.hasText(httpServletRequest.getQueryString()) ? httpServletRequest.getQueryString() : ""
            );
        } else params = LogUtil.getParameters(joinPoint);

        log.info("<<< path(s): {}, method(s): {}, arguments: {} >>> ",
                httpServletRequest.getServletPath(),
                method,
                Gson.getInstance().toJson(params));

    }

    // after method return response
    @AfterReturning(pointcut = "pointcut()", returning = "object")
    @SneakyThrows
    public void logMethodAfterReturn(JoinPoint joinPoint, Object object) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String method = signature.getMethod().getName();

        log.info("<<< path(s): {}, method(s): {}, retuning: {} >>> ",
                httpServletRequest.getServletPath(),
                method,
                Gson.getInstance().toJson(object));
    }

    // after throw exception
    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    @SneakyThrows
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String method = signature.getMethod().getName();

        log.info("<<< path(s): {}, method(s): {}, errorMessage: {} >>>",
                httpServletRequest.getServletPath(),
                method,
                ex.getMessage());

        log.error("<<< error details >>> ", ex);
    }

}
