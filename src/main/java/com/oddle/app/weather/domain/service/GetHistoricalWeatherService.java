package com.oddle.app.weather.domain.service;

import com.oddle.app.weather.application.model.response.*;
import com.oddle.app.weather.domain.entity.Weather;
import com.oddle.app.weather.domain.service.dispatcher.IGetHistoricalWeatherService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.service
 */
@RequiredArgsConstructor
@Slf4j
public class GetHistoricalWeatherService implements IGetHistoricalWeatherService {

    private final WeatherConfigConstant configConstant;

    private final IGetLocationService getLocation;

    @Override
    public void validate(Map<String, String> request) {

    }

    @Override
    public Response process(Map<String, String> request) {

        LocationResponse locationResponse = getLocation.execute(request.getOrDefault("city", ""));

        log.info("getHistoricalWeather.process.locationResponse: {}", Gson.getInstance().toJson(locationResponse));

        if (Objects.isNull(locationResponse)) {
            log.info("getHistoricalWeather.process.locationResponse null");
            throw new InternalServerException("error.current_weather.get_location");
        }

        HistoricalResponse historicalResponse = getHistoricalWeather(
                locationResponse.getLat(),
                locationResponse.getLon(),
                request);

        if (Objects.isNull(historicalResponse)) {
            log.info("getHistoricalWeather.process.historicalResponse null");
            throw new InternalServerException("error.historical_weather.get_historical");
        }

        final String TOTAL_KEY = "total";
        final String HISTORY_KEY = "histories";

        List<Weather> histories = new LinkedList<>();

        if (CollectionUtils.isEmpty(historicalResponse.getList())) {

            log.info("getHistoricalWeather.process.historyList empty");

            return ResponseFactory.produce(ResponseStatusEnum.SUCCESS, Map.of(
                    TOTAL_KEY, 0,
                    HISTORY_KEY, histories
            ), List.of());
        }

        historicalResponse.getList().stream().forEach(h -> {
            List<WeatherResponse.WeatherObjectResponse> listWeather = h.getWeather();
            WeatherResponse.MainWeatherObjectResponse mainObject = h.getMain();
            WeatherResponse.CloudObjectResponse cloudObject = h.getClouds();
            WeatherResponse.WindObjectResponse windObject = h.getWind();

            histories.add(Weather
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
                    .visibility(h.getVisibility())
                    .cloud(cloudObject.getAll())
                    .windSpeed(windObject.getSpeed())
                    .windDeg(windObject.getDeg())
                    .windGust(windObject.getGust())
                    .dt(h.getDt())
                    .dateTime(DateUtil.formatDateTime(DateUtil.parseDateFromEpoch(h.getDt()), DateUtil.DATE_HOUR_FORMAT))
                    .build());
        });

        log.info("getHistoricalWeather.process.historiesSize: {}", histories.size());

        return ResponseFactory.produce(ResponseStatusEnum.SUCCESS, Map.of(
                TOTAL_KEY, histories.size(),
                HISTORY_KEY, histories
        ), List.of());
    }

    private HistoricalResponse getHistoricalWeather(BigDecimal lat,
                                                    BigDecimal lon,
                                                    Map<String, String> request) {
        String url = new StringBuilder().append(configConstant.getHistoricalHost()).append("/data/2.5/history/city").toString();

        log.info("getHistoricalWeather.getHistoricalWeather.url: {}", url);

        String type = request.getOrDefault("type", "hour");
        String start = request.getOrDefault("start", "");
        String end = request.getOrDefault("end", "");

        Long startUnix = DateUtil.parseDateTime(start, DateUtil.DATE_HOUR_FORMAT).toInstant().getEpochSecond();
        Long endUnix = DateUtil.parseDateTime(end, DateUtil.DATE_HOUR_FORMAT).toInstant().getEpochSecond();

        Map<String, Object> params = Map.of(
                configConstant.getLatParam(), lat,
                configConstant.getLonParam(), lon,
                configConstant.getTypeParam(), StringUtils.hasText(type) ? type : "hour",
                configConstant.getStartParam(), startUnix,
                configConstant.getEndParam(), endUnix,
                configConstant.getAppParam(), configConstant.getApiKey()
        );

        log.info("getHistoricalWeather.getHistoricalWeather.params: {}", params);

        HttpResponse<String> httpResponse = RestApiByKongUtil.get(
                url,
                Map.of(),
                params
        );

        if (httpResponse.isSuccess() && StringUtils.hasText(httpResponse.getBody())) {
            HistoricalResponse historicalResponse = Gson.getInstance().fromJson(httpResponse.getBody(), HistoricalResponse.class);

            log.info("getHistoricalWeather.getHistoricalWeather.weatherResponse: {}", httpResponse.getBody());

            return historicalResponse;
        }

        return null;

    }

}
