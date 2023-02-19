package com.oddle.app.weather.infrastructure.builder;

import java.util.Objects;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.builder
 */
public class Gson {

    private Gson () {}

    private static volatile com.google.gson.Gson instance;

    public static com.google.gson.Gson getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (com.google.gson.Gson.class) {
                if (Objects.isNull(instance)) {
                    instance = new com.google.gson.Gson();
                }
            }
        }
        // Do something after get instance ...
        return instance;
    }

}
