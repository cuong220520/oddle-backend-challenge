package com.oddle.app.weather.domain.service;

import com.google.gson.reflect.TypeToken;
import com.oddle.app.weather.application.model.response.LocationResponse;
import com.oddle.app.weather.domain.service.dispatcher.IGetLocationService;
import com.oddle.app.weather.infrastructure.builder.Gson;
import com.oddle.app.weather.infrastructure.constant.WeatherConfigConstant;
import com.oddle.app.weather.infrastructure.exception.BadRequestException;
import com.oddle.app.weather.infrastructure.util.RestApiByKongUtil;
import kong.unirest.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.service
 */
@RequiredArgsConstructor
@Slf4j
public class GetLocationService implements IGetLocationService {

    private final WeatherConfigConstant configConstant;

    @Override
    public void validate(String city) {
        if (!StringUtils.hasText(city)) throw new BadRequestException("field.not.blank");
    }

    @Override
    public LocationResponse process(String city) {
        String url = new StringBuilder().append(configConstant.getApiHost()).append("/geo/1.0/direct").toString();

        log.info("getLocation.process.url: {}", url);

        Map<String, Object> params = Map.of(
                configConstant.getQueryParam(), city,
                configConstant.getLimitParam(), 1,
                configConstant.getAppParam(), configConstant.getApiKey()
        );

        log.info("getLocation.process.params: {}", params);

        HttpResponse<String> httpResponse = RestApiByKongUtil.get(
                url,
                Map.of(),
                params
        );

        if (httpResponse.isSuccess() && StringUtils.hasText(httpResponse.getBody())) {
            List<LocationResponse> locationResponses = Gson.getInstance().fromJson(httpResponse.getBody(), new TypeToken<List<LocationResponse>>() {
            }.getType());

            log.info("getLocation.process.locationResponses: {}", httpResponse.getBody());

            if (!CollectionUtils.isEmpty(locationResponses)) return locationResponses.get(0);
        }

        return null;
    }
}
