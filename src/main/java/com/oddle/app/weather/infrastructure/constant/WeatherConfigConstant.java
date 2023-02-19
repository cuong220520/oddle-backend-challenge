package com.oddle.app.weather.infrastructure.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.config
 */
@Configuration
@ConfigurationProperties(prefix = "app.config.const.weather")
@Getter
@Setter
public class WeatherConfigConstant {

    private String apiKey;
    private String apiHost;
    private String queryParam;
    private String limitParam;
    private String appParam;
    private String latParam;
    private String lonParam;
    private String typeParam;
    private String startParam;
    private String endParam;
    private String historicalHost;

}
