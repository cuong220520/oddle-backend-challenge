package com.oddle.app.weather.infrastructure.util;

import com.oddle.app.weather.infrastructure.builder.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.util
 */
public class LogUtil {

    private LogUtil() {}

    public static Map<String, String> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        HashMap<String, String> map = new HashMap<>();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], Gson.getInstance().toJson(joinPoint.getArgs()[0]));
        }

        return map;
    }

}
