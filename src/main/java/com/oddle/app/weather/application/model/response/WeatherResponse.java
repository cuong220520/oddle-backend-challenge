package com.oddle.app.weather.application.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.application.model.response
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse implements Serializable {

    private static final long serialVersionUID = 1l;

    private List<WeatherObjectResponse> weather;
    private MainWeatherObjectResponse main;
    private WindObjectResponse wind;
    private CloudObjectResponse clouds;
    private SysObjectResponse sys;
    private Integer visibility;
    private Long dt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherObjectResponse {
        private String main;
        private String description;
        private String icon;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MainWeatherObjectResponse {
        private Double temp;
        private Double feels_like;
        private Double temp_min;
        private Double temp_max;
        private Double pressure;
        private Double humidity;
        private Double sea_level;
        private Double grnd_level;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WindObjectResponse {
        private Double speed;
        private Double deg;
        private Double gust;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CloudObjectResponse {
        private Integer all;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SysObjectResponse {
        private String type;
        private String country;
        private Long sunrise;
        private Long sunset;
    }

}
