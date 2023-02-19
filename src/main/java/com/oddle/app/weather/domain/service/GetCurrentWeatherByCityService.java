package com.oddle.app.weather.domain.service;

import com.oddle.app.weather.application.model.response.WeatherResponse;
import com.oddle.app.weather.application.model.response.LocationResponse;
import com.oddle.app.weather.application.model.response.Response;
import com.oddle.app.weather.application.model.response.ResponseFactory;
import com.oddle.app.weather.domain.entity.Weather;
import com.oddle.app.weather.domain.service.dispatcher.IGetCurrentWeatherByCityService;
import com.oddle.app.weather.domain.service.dispatcher.IGetLocationService;
import com.oddle.app.weather.infrastructure.builder.Gson;
import com.oddle.app.weather.infrastructure.constant.WeatherConfigConstant;
import com.oddle.app.weather.infrastructure.enumurate.ResponseStatusEnum;
import com.oddle.app.weather.infrastructure.exception.InternalServerException;
import com.oddle.app.weather.infrastructure.util.DateUtil;
import com.oddle.app.weather.infrastructure.util.RestApiByKongUtil;
import kong.unirest.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.service.dispatcher
 */
@RequiredArgsConstructor
@Slf4j
public class GetCurrentWeatherByCityService implements IGetCurrentWeatherByCityService {

    private final WeatherConfigConstant configConstant;

    private final IGetLocationService getLocation;

    @Override
    public void validate(String request) {
    }

    @Override
    public Response process(String request) {

        LocationResponse locationResponse = getLocation.execute(request);

        log.info("getCurrentWeatherByCity.process.locationResponse: {}", Gson.getInstance().toJson(locationResponse));

        if (Objects.isNull(locationResponse)) {
            log.info("getCurrentWeatherByCity.process.locationResponse null");
            throw new InternalServerException("error.current_weather.get_location");
        }

        WeatherResponse weatherResponse = getWeather(locationResponse.getLat(), locationResponse.getLon());

        if (Objects.isNull(weatherResponse)) {
            log.info("getCurrentWeatherByCity.process.currentWeatherResponse null");
            throw new InternalServerException("error.current_weather.get_weather");
        }

        List<WeatherResponse.WeatherObjectResponse> listWeather = weatherResponse.getWeather();
        WeatherResponse.MainWeatherObjectResponse mainObject = weatherResponse.getMain();
        WeatherResponse.CloudObjectResponse cloudObject = weatherResponse.getClouds();
        WeatherResponse.SysObjectResponse sysObject = weatherResponse.getSys();
        WeatherResponse.WindObjectResponse windObject = weatherResponse.getWind();

        final String dateTimeFormat = "HH:mm:ss";

        Weather weather = Weather
                .builder()
                .mainStatus(listWeather.get(0).getMain())
                .description(listWeather.get(0).getDescription())
                .icon(listWeather.get(0).getIcon())
                .temp(mainObject.getTemp())
                .feelsLike(mainObject.getFeels_like())
                .tempMax(mainObject.getTemp_max())
                .tempMin(mainObject.getTemp_min())
                .pressure(mainObject.getPressure())
                .humidity(mainObject.getHumidity())
                .seaLevel(mainObject.getSea_level())
                .grndLevel(mainObject.getGrnd_level())
                .visibility(weatherResponse.getVisibility())
                .cloud(cloudObject.getAll())
                .windSpeed(windObject.getSpeed())
                .windDeg(windObject.getDeg())
                .windGust(windObject.getGust())
                .sunrise(DateUtil.formatDateTime(DateUtil.parseDateFromEpoch(sysObject.getSunrise()), dateTimeFormat))
                .sunset(DateUtil.formatDateTime(DateUtil.parseDateFromEpoch(sysObject.getSunset()), dateTimeFormat))
                .build();

        return ResponseFactory.produce(ResponseStatusEnum.SUCCESS,
                weather,
                List.of());
    }

    private WeatherResponse getWeather(BigDecimal lat, BigDecimal lon) {
        String url = new StringBuilder().append(configConstant.getApiHost()).append("/data/2.5/weather").toString();

        log.info("getCurrentWeatherByCity.getWeather.url: {}", url);

        Map<String, Object> params = Map.of(
                configConstant.getLatParam(), lat,
                configConstant.getLonParam(), lon,
                configConstant.getAppParam(), configConstant.getApiKey()
        );

        log.info("getCurrentWeatherByCity.getWeather.params: {}", params);

        HttpResponse<String> httpResponse = RestApiByKongUtil.get(
                url,
                Map.of(),
                params
        );

        if (httpResponse.isSuccess() && StringUtils.hasText(httpResponse.getBody())) {
            WeatherResponse weatherResponse = Gson.getInstance().fromJson(httpResponse.getBody(), WeatherResponse.class);

            log.info("getCurrentWeatherByCity.getWeather.currentWeatherResponse: {}", httpResponse.getBody());

            return weatherResponse;
        }

        return null;

    }

}
